package com.minorproject.cardscannerai.ui.screen

import android.content.Intent
import android.provider.ContactsContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.minorproject.cardscannerai.ui.viewmodel.ContactsViewModel

@Composable
fun ContactDetailScreen(
    id: Long,
    contactsViewModel: ContactsViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by contactsViewModel.uiState.collectAsState()
    val contact = uiState.contacts.firstOrNull { it.id == id }
    val colors = MaterialTheme.colorScheme
    val background = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.surfaceVariant.copy(alpha = 0.45f),
            colors.secondaryContainer.copy(alpha = 0.35f)
        )
    )

    if (contact == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(onClick = onBack) { Text("Back") }
            Text(text = "Contact not found", style = MaterialTheme.typography.headlineSmall)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TextButton(onClick = onBack) { Text("Back") }

        ElevatedCard(shape = RoundedCornerShape(28.dp)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = contact.name?.value ?: "Unnamed", style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = contact.company?.value.orEmpty().ifBlank { "No company provided" },
                    color = colors.onSurfaceVariant
                )
                Text(
                    text = contact.category.name.lowercase().replaceFirstChar { it.uppercase() },
                    color = colors.primary
                )
            }
        }

        DetailCard(label = "Phone", value = contact.phone?.value)
        DetailCard(label = "Email", value = contact.email?.value)
        DetailCard(label = "Designation", value = contact.designation?.value)
        DetailCard(label = "Address", value = contact.address?.value)
        DetailCard(label = "Website", value = contact.website?.value)

        ElevatedCard(shape = RoundedCornerShape(24.dp)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Actions", style = MaterialTheme.typography.titleMedium)
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    val intent = Intent(Intent.ACTION_INSERT).apply {
                        type = ContactsContract.Contacts.CONTENT_TYPE
                        putExtra(ContactsContract.Intents.Insert.NAME, contact.name?.value)
                        putExtra(ContactsContract.Intents.Insert.PHONE, contact.phone?.value)
                        putExtra(ContactsContract.Intents.Insert.EMAIL, contact.email?.value)
                        putExtra(ContactsContract.Intents.Insert.COMPANY, contact.company?.value)
                        putExtra(ContactsContract.Intents.Insert.JOB_TITLE, contact.designation?.value)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Save to Phone Contacts")
                }

                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    val vCard = buildString {
                        appendLine("BEGIN:VCARD")
                        appendLine("VERSION:3.0")
                        appendLine("FN:${contact.name?.value.orEmpty()}")
                        appendLine("ORG:${contact.company?.value.orEmpty()}")
                        appendLine("TITLE:${contact.designation?.value.orEmpty()}")
                        appendLine("TEL:${contact.phone?.value.orEmpty()}")
                        appendLine("EMAIL:${contact.email?.value.orEmpty()}")
                        appendLine("ADR:${contact.address?.value.orEmpty()}")
                        appendLine("URL:${contact.website?.value.orEmpty()}")
                        appendLine("END:VCARD")
                    }
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/x-vcard"
                        putExtra(Intent.EXTRA_TEXT, vCard)
                    }
                    context.startActivity(Intent.createChooser(sendIntent, "Share vCard"))
                }) {
                    Text("Share as vCard")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun DetailCard(label: String, value: String?) {
    val colors = MaterialTheme.colorScheme
    ElevatedCard(shape = RoundedCornerShape(22.dp)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = colors.onSurfaceVariant)
            Text(text = value?.takeIf { it.isNotBlank() } ?: "Not provided", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
