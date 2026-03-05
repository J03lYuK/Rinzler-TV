package uk.rinzler.tv.constant

sealed interface CustomMessage {
	data object RefreshCurrentItem : CustomMessage
	data object ActionComplete : CustomMessage
}
