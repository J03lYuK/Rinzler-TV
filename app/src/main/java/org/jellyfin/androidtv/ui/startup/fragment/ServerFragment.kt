package uk.rinzler.tv.ui.startup.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uk.rinzler.tv.R
import uk.rinzler.tv.auth.model.ApiClientErrorLoginState
import uk.rinzler.tv.auth.model.AuthenticatedState
import uk.rinzler.tv.auth.model.AuthenticatingState
import uk.rinzler.tv.auth.model.PrivateUser
import uk.rinzler.tv.auth.model.RequireSignInState
import uk.rinzler.tv.auth.model.Server
import uk.rinzler.tv.auth.model.ServerUnavailableState
import uk.rinzler.tv.auth.model.ServerVersionNotSupported
import uk.rinzler.tv.auth.model.User
import uk.rinzler.tv.auth.repository.AuthenticationRepository
import uk.rinzler.tv.auth.repository.ServerRepository
import uk.rinzler.tv.auth.repository.ServerUserRepository
import uk.rinzler.tv.data.service.BackgroundService
import uk.rinzler.tv.databinding.FragmentServerBinding
import uk.rinzler.tv.ui.card.UserCardView
import uk.rinzler.tv.ui.startup.PinEntryDialog
import uk.rinzler.tv.ui.startup.StartupViewModel
import uk.rinzler.tv.util.ListAdapter
import uk.rinzler.tv.util.MarkdownRenderer
import uk.rinzler.tv.util.PinCodeUtil
import org.jellyfin.sdk.model.serializer.toUUIDOrNull
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ServerFragment : Fragment() {
	companion object {
		const val ARG_SERVER_ID = "server_id"
	}

	private val startupViewModel: StartupViewModel by activityViewModel()
	private val markdownRenderer: MarkdownRenderer by inject()
	private val authenticationRepository: AuthenticationRepository by inject()
	private val serverUserRepository: ServerUserRepository by inject()
	private val backgroundService: BackgroundService by inject()
	private var _binding: FragmentServerBinding? = null
	private val binding get() = _binding!!

	private val serverIdArgument get() = arguments?.getString(ARG_SERVER_ID)?.ifBlank { null }?.toUUIDOrNull()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val server = serverIdArgument?.let(startupViewModel::getServer)

		if (server == null) {
			navigateFragment<SelectServerFragment>(keepToolbar = true, keepHistory = false)
			return null
		}

		_binding = FragmentServerBinding.inflate(inflater, container, false)

	val userAdapter = UserAdapter(requireContext(), server, startupViewModel, authenticationRepository, serverUserRepository)
	userAdapter.onItemPressed = { user ->
		if (PinCodeUtil.isPinEnabled(requireContext(), user.id)) {
			showPinEntry(server, user)
		} else {
			authenticateUser(server, user)
		}
	}
	binding.users.adapter = userAdapter

	startupViewModel.users
		.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
		.onEach { users ->
				userAdapter.items = users

				binding.users.post {
					val parentWidth = (binding.users.parent as? View)?.width ?: 0

					if (parentWidth > 0 && users.isNotEmpty()) {
						val density = resources.displayMetrics.density
						val cardWidthPx = (130 * density).toInt()
						val itemSpacingPx = (16 * density).toInt()
						val totalContentWidth = (cardWidthPx * users.size) + (itemSpacingPx * (users.size - 1))
						val padding = maxOf(0, (parentWidth - totalContentWidth) / 2)
						binding.users.setPadding(padding, 0, padding, 0)
					}
				}

				binding.users.isFocusable = users.any()
				binding.noUsersWarning.isVisible = users.isEmpty()

				val hasUsers = users.isNotEmpty()
				binding.editButton.isVisible = hasUsers
				binding.actionsContainer.isVisible = !hasUsers

				if (hasUsers) {
					binding.users.requestFocus()
				} else {
					binding.addUserButton.requestFocus()
				}
			}.launchIn(viewLifecycleOwner.lifecycleScope)

		binding.editButton.setOnClickListener {
			binding.actionsContainer.isVisible = true
			binding.editButton.isVisible = false
			binding.addUserButton.requestFocus()
		}

		startupViewModel.loadUsers(server)

		onServerChange(server)

		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
				val updated = startupViewModel.updateServer(server)
				if (updated) startupViewModel.getServer(server.id)?.let(::onServerChange)
			}
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}

	private fun showPinEntry(server: Server, user: User) {
		PinEntryDialog.show(
			context = requireContext(),
			mode = PinEntryDialog.Mode.VERIFY,
			onComplete = { pin ->
				if (pin != null) {
					val userPrefs = uk.rinzler.tv.preference.UserSettingPreferences(requireContext(), user.id)
					val storedHash = userPrefs[uk.rinzler.tv.preference.UserSettingPreferences.userPinHash]

					if (PinCodeUtil.hashPin(pin) == storedHash) {
						authenticateUser(server, user)
					} else {
						Toast.makeText(context, R.string.lbl_pin_code_incorrect, Toast.LENGTH_SHORT).show()
						binding.root.postDelayed({
							showPinEntry(server, user)
						}, 500)
					}
				} else {
					binding.users.requestFocus()
				}
			},
			onForgotPin = {
				navigateFragment<UserLoginFragment>(bundleOf(
					UserLoginFragment.ARG_SERVER_ID to server.id.toString(),
					UserLoginFragment.ARG_USERNAME to user.name,
				))
			}
		)
	}

	private fun authenticateUser(server: Server, user: User) {
		startupViewModel.authenticate(server, user).onEach { state ->
			when (state) {
				// Ignored states
				AuthenticatingState -> Unit
				AuthenticatedState -> Unit
				// Actions
				RequireSignInState -> navigateFragment<UserLoginFragment>(bundleOf(
					UserLoginFragment.ARG_SERVER_ID to server.id.toString(),
					UserLoginFragment.ARG_USERNAME to user.name,
				))
				// Errors
				ServerUnavailableState,
				is ApiClientErrorLoginState -> Toast.makeText(context, R.string.server_connection_failed, Toast.LENGTH_LONG).show()

				is ServerVersionNotSupported -> Toast.makeText(
					context,
					getString(
						R.string.server_issue_outdated_version,
						state.server.version,
						ServerRepository.recommendedServerVersion.toString()
					),
					Toast.LENGTH_LONG
				).show()
			}
		}.launchIn(lifecycleScope)
	}

	private fun onServerChange(server: Server) {
		binding.loginDisclaimer.text = server.loginDisclaimer?.let { markdownRenderer.toMarkdownSpanned(it) }

		binding.serverName.text = server.name
		binding.serverName.isVisible = server.name.isNotBlank()

		binding.addUserButton.setOnClickListener {
			navigateFragment<UserLoginFragment>(
				args = bundleOf(
					UserLoginFragment.ARG_SERVER_ID to server.id.toString(),
					UserLoginFragment.ARG_USERNAME to null
				)
			)
		}

		binding.serverButton.setOnClickListener {
			navigateFragment<SelectServerFragment>(keepToolbar = true)
		}

		if (!server.versionSupported) {
			binding.notification.isVisible = true
			binding.notification.text = getString(
				R.string.server_unsupported_notification,
				server.version,
				ServerRepository.recommendedServerVersion.toString(),
			)
		} else if (!server.setupCompleted) {
			binding.notification.isVisible = true
			binding.notification.text = getString(R.string.server_setup_incomplete)
		} else {
			binding.notification.isGone = true
		}
	}

	private inline fun <reified F : Fragment> navigateFragment(
		args: Bundle = bundleOf(),
		keepToolbar: Boolean = false,
		keepHistory: Boolean = true,
	) {
		requireActivity()
			.supportFragmentManager
			.commit {
				if (keepToolbar) {
					replace<F>(R.id.content_view, null, args)
					replace<StartupToolbarFragment>(R.id.toolbar_view)
				} else {
					replace<F>(R.id.content_view, null, args)
				}

				if (keepHistory) addToBackStack(null)
			}
	}

	override fun onResume() {
		super.onResume()

		startupViewModel.reloadStoredServers()
		backgroundService.clearBackgrounds()

		val server = serverIdArgument?.let(startupViewModel::getServer)
		if (server != null) startupViewModel.loadUsers(server)
		else navigateFragment<SelectServerFragment>(keepToolbar = true)
	}

	private class UserAdapter(
		private val context: Context,
		private val server: Server,
		private val startupViewModel: StartupViewModel,
		private val authenticationRepository: AuthenticationRepository,
		private val serverUserRepository: ServerUserRepository,
	) : ListAdapter<User, UserAdapter.ViewHolder>() {
		var onItemPressed: (User) -> Unit = {}

		override fun areItemsTheSame(old: User, new: User): Boolean = old.id == new.id

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
			val cardView = UserCardView(context)

			return ViewHolder(cardView)
		}

		override fun onBindViewHolder(holder: ViewHolder, user: User) {
			holder.cardView.name = user.name
			holder.cardView.image = startupViewModel.getUserImage(server, user)

			holder.cardView.setPopupMenu {
				if (user is PrivateUser && user.accessToken != null) {
					item(context.getString(R.string.lbl_sign_out)) {
						authenticationRepository.logout(user)
					}
				}

				if (user is PrivateUser) {
					item(context.getString(R.string.lbl_remove)) {
						serverUserRepository.deleteStoredUser(user)
						startupViewModel.loadUsers(server)
					}
				}
			}

			holder.cardView.setOnClickListener {
				onItemPressed(user)
			}
		}

		private class ViewHolder(
			val cardView: UserCardView,
		) : RecyclerView.ViewHolder(cardView)
	}
}
