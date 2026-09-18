package com.minorproject.cardscannerai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.cardscannerai.domain.repository.ContactRepository
import com.minorproject.cardscannerai.ui.state.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.isCloudSyncEnabled().collectLatest { enabled ->
                _uiState.value = SettingsUiState(cloudSyncEnabled = enabled)
            }
        }
    }

    fun setCloudSync(enabled: Boolean) {
        viewModelScope.launch {
            repository.setCloudSyncEnabled(enabled)
        }
    }
}
