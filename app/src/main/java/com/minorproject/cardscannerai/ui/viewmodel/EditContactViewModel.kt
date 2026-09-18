package com.minorproject.cardscannerai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.model.ContactCategory
import com.minorproject.cardscannerai.domain.model.ExtractedField
import com.minorproject.cardscannerai.domain.repository.ContactRepository
import com.minorproject.cardscannerai.ui.state.EditContactUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditContactViewModel(
    private val repository: ContactRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditContactUiState())
    val uiState: StateFlow<EditContactUiState> = _uiState.asStateFlow()

    fun setContact(contact: Contact) {
        _uiState.value = _uiState.value.copy(contact = contact)
    }

    fun updateField(field: String, value: String) {
        val current = _uiState.value.contact
        val updated = when (field) {
            "name" -> current.copy(name = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            "phone" -> current.copy(phone = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            "email" -> current.copy(email = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            "company" -> current.copy(company = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            "designation" -> current.copy(designation = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            "address" -> current.copy(address = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            "website" -> current.copy(website = value.ifBlank { null }?.let { ExtractedField(it, 1f) })
            else -> current
        }
        _uiState.value = _uiState.value.copy(contact = updated)
    }

    fun updateCategory(category: ContactCategory) {
        _uiState.value = _uiState.value.copy(contact = _uiState.value.contact.copy(category = category))
    }

    fun save() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, error = null)
            runCatching {
                repository.saveContact(_uiState.value.contact)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isSaving = false, saveSuccess = true)
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = throwable.message ?: "Save failed"
                )
            }
        }
    }

    fun consumeSaveSuccess() {
        _uiState.value = _uiState.value.copy(saveSuccess = false)
    }
}
