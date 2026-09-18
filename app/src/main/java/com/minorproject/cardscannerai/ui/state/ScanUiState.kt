package com.minorproject.cardscannerai.ui.state

import android.net.Uri

data class ScanUiState(
    val selectedImageUri: Uri? = null,
    val selectedImageUris: List<Uri> = emptyList(),
    val capturedPageCount: Int = 0,
    val isProcessing: Boolean = false,
    val error: String? = null
)
