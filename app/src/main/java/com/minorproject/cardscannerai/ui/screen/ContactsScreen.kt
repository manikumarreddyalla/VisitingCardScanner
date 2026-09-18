package com.minorproject.cardscannerai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.minorproject.cardscannerai.ui.viewmodel.ContactsViewModel

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel,
    onBack: () -> Unit,
    onOpenDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme
    val background = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.surfaceVariant.copy(alpha = 0.5f),
            colors.primaryContainer.copy(alpha = 0.38f)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Saved Contacts", style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = "Browse and manage the contacts extracted from your cards.",
                    color = colors.onSurfaceVariant
                )
            }
            TextButton(onClick = onBack) {
                Text("Back")
            }
        }

        ElevatedCard(shape = RoundedCornerShape(24.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Search")
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.query,
                    onValueChange = viewModel::onSearchQueryChanged,
                    label = { Text("Search contacts") },
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = { }, label = { Text("${uiState.contacts.size} visible") })
                    AssistChip(onClick = { }, label = { Text(if (uiState.query.isBlank()) "All contacts" else "Filtered results") })
                }
            }
        }

        if (uiState.loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (uiState.contacts.isEmpty()) {
            ElevatedCard(shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "No contacts yet", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (uiState.query.isBlank()) {
                            "Scan a card to start building your contact list."
                        } else {
                            "No saved contact matches the current search."
                        },
                        color = colors.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(uiState.contacts, key = { it.id }) { contact ->
                    ElevatedCard(shape = RoundedCornerShape(24.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenDetail(contact.id) }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(54.dp),
                                shape = CircleShape,
                                color = colors.primaryContainer.copy(alpha = 0.9f)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = contact.name?.value.orEmpty().firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = colors.primary
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(text = contact.name?.value ?: "Unnamed", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = contact.company?.value.orEmpty().ifBlank { "No company listed" },
                                    color = colors.onSurfaceVariant
                                )
                                Text(
                                    text = contact.category.name.lowercase().replaceFirstChar { it.uppercase() },
                                    color = colors.secondary
                                )
                            }

                            TextButton(onClick = { viewModel.deleteContact(contact.id) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}
