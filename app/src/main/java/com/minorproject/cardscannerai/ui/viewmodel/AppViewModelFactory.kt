package com.minorproject.cardscannerai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minorproject.cardscannerai.di.AppContainer

class AppViewModelFactory(
    private val container: AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(container.cardScanRepository) as T
            }

            modelClass.isAssignableFrom(EditContactViewModel::class.java) -> {
                EditContactViewModel(container.contactRepository) as T
            }

            modelClass.isAssignableFrom(ContactsViewModel::class.java) -> {
                ContactsViewModel(container.contactRepository) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(container.contactRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
