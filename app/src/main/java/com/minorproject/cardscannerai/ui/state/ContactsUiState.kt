package com.minorproject.cardscannerai.ui.state

import com.minorproject.cardscannerai.domain.model.Contact

data class ContactsUiState(
    val contacts: List<Contact> = emptyList(),
    val query: String = "",
    val loading: Boolean = false
)
