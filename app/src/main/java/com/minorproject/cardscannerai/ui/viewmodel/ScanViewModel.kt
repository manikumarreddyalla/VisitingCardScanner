package com.minorproject.cardscannerai.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.repository.CardScanRepository
import com.minorproject.cardscannerai.ui.state.ScanUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanViewModel(
    private val cardScanRepository: CardScanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    private val _parsedContact = MutableStateFlow<Contact?>(null)
    val parsedContact: StateFlow<Contact?> = _parsedContact.asStateFlow()

    fun onImageCaptured(uri: Uri) {
        onImagesCaptured(listOf(uri))
    }

    fun processCard(uri: Uri) {
        processCard(listOf(uri))
    }

    fun onImagesCaptured(imageUris: List<Uri>) {
        val primaryImage = imageUris.firstOrNull()
        _uiState.value = _uiState.value.copy(
            selectedImageUri = primaryImage,
            selectedImageUris = imageUris,
            capturedPageCount = imageUris.size,
            error = null
        )
    }

    fun processCard(imageUris: List<Uri>) {
        _uiState.value = _uiState.value.copy(isProcessing = true, error = null)

        viewModelScope.launch {
            if (imageUris.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isProcessing = false,
                    error = "No scanned image was captured"
                )
                return@launch
            }

            runCatching {
                withContext(Dispatchers.IO) {
                    cardScanRepository.processCardImages(imageUris)
                }
            }.onSuccess { contact ->
                _parsedContact.value = contact
                _uiState.value = _uiState.value.copy(isProcessing = false)
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isProcessing = false,
                    error = throwable.message ?: "Unable to process card"
                )
            }
        }
    }

    fun clearParsedContact() {
        _parsedContact.value = null
    }
}
