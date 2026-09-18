package com.minorproject.cardscannerai.ui.state

import com.minorproject.cardscannerai.domain.model.Contact
import com.minorproject.cardscannerai.domain.model.ContactCategory

data class EditContactUiState(
    val contact: Contact = Contact(),
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
) {
    val categories: List<ContactCategory> = ContactCategory.entries
}
