package com.minorproject.cardscannerai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.cardscannerai.domain.repository.ContactRepository
import com.minorproject.cardscannerai.ui.state.ContactsUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val repository: ContactRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactsUiState(loading = true))
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()
    private var observeJob: Job? = null

    init {
        observeContacts()
    }

    private fun observeContacts() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            repository.observeContacts(_uiState.value.query).collectLatest { list ->
                _uiState.value = _uiState.value.copy(contacts = list, loading = false)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(query = query, loading = true)
        observeContacts()
    }

    fun deleteContact(id: Long) {
        viewModelScope.launch {
            repository.deleteContact(id)
        }
    }
}
