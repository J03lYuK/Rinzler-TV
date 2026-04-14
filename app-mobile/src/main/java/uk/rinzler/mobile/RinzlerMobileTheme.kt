package uk.rinzler.mobile

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RinzlerMobileColors = darkColorScheme(
	primary = Color(0xFF5CD6F7),
	onPrimary = Color(0xFF001018),
	secondary = Color(0xFF7BE7FF),
	background = Color(0xFF03050A),
	surface = Color(0xFF0B1822),
	onSurface = Color(0xFFF2F7FA),
)

@Composable
fun RinzlerMobileTheme(
	content: @Composable () -> Unit,
) {
	MaterialTheme(
		colorScheme = RinzlerMobileColors,
		content = content,
	)
}
