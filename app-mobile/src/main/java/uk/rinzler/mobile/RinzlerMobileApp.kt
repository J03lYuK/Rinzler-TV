package uk.rinzler.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RinzlerMobileApp() {
	RinzlerMobileTheme {
		Scaffold { innerPadding ->
			LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.background(
						Brush.verticalGradient(
							colors = listOf(Color(0xFF02131D), Color(0xFF05070C), Color.Black),
						),
					),
				contentPadding = PaddingValues(
					start = 20.dp,
					top = innerPadding.calculateTopPadding() + 20.dp,
					end = 20.dp,
					bottom = innerPadding.calculateBottomPadding() + 24.dp,
				),
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				item {
					Text(
						text = "Rinzler Mobile",
						style = MaterialTheme.typography.displaySmall,
						fontWeight = FontWeight.Bold,
						color = Color.White,
					)
				}
				item {
					Text(
						text = "Phone-first app scaffold for shared Rinzler logic.",
						style = MaterialTheme.typography.titleMedium,
						color = Color(0xFFA9DFF7),
					)
				}
				item {
					MobilePlanCard(
						title = "Why This Module Exists",
						body = "This is a clean mobile entry point inside the same repo. The TV app stays stable while auth, data, and playback logic can be extracted here over time.",
					)
				}
				item {
					MobilePlanCard(
						title = "First MVP Screens",
						body = "Login, server selection, home, search, item details, playback, and settings. All rebuilt for touch, portrait layouts, and smaller screens.",
					)
				}
				item {
					MobilePlanCard(
						title = "Shared Code To Migrate Next",
						body = "Auth, repositories, Jellyfin/Emby server modules, Jellyseerr integration, and playback plumbing are the best first candidates to move out of the TV app.",
					)
				}
				item {
					MobilePlanCard(
						title = "TV Code To Leave Alone",
						body = "Leanback navigation, D-pad focus flows, TV fragments, and home-row browsing patterns should stay in the TV app instead of being adapted for phones.",
					)
				}
			}
		}
	}
}

@Composable
private fun MobilePlanCard(
	title: String,
	body: String,
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		shape = RoundedCornerShape(24.dp),
		colors = CardDefaults.cardColors(
			containerColor = Color(0xCC0B1822),
		),
	) {
		Column(
			modifier = Modifier.padding(20.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.titleLarge,
				fontWeight = FontWeight.SemiBold,
				color = Color(0xFF7EE7FF),
			)
			Text(
				text = body,
				style = MaterialTheme.typography.bodyLarge,
				color = Color(0xFFE6EEF5),
			)
		}
	}
}
