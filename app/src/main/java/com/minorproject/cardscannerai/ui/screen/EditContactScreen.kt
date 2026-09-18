package com.minorproject.cardscannerai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.minorproject.cardscannerai.domain.model.ContactCategory
import com.minorproject.cardscannerai.ui.viewmodel.EditContactViewModel

@Composable
fun EditContactScreen(
    viewModel: EditContactViewModel,
    onSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme
    val background = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.secondaryContainer.copy(alpha = 0.34f),
            colors.primaryContainer.copy(alpha = 0.42f)
        )
    )

    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            viewModel.consumeSaveSuccess()
            onSaved()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ElevatedCard(shape = RoundedCornerShape(26.dp)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Edit Contact", style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = "Review the extracted details before saving them to your contacts.",
                    color = colors.onSurfaceVariant
                )
            }
        }

        ElevatedCard(shape = RoundedCornerShape(26.dp)) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Identity", style = MaterialTheme.typography.titleMedium)
                Field("Name", uiState.contact.name?.value.orEmpty()) { viewModel.updateField("name", it) }
                Field("Phone", uiState.contact.phone?.value.orEmpty()) { viewModel.updateField("phone", it) }
                Field("Email", uiState.contact.email?.value.orEmpty()) { viewModel.updateField("email", it) }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Business Details", style = MaterialTheme.typography.titleMedium)
                Field("Company", uiState.contact.company?.value.orEmpty()) { viewModel.updateField("company", it) }
                Field("Designation", uiState.contact.designation?.value.orEmpty()) { viewModel.updateField("designation", it) }
                Field("Address", uiState.contact.address?.value.orEmpty()) { viewModel.updateField("address", it) }
                Field("Website", uiState.contact.website?.value.orEmpty()) { viewModel.updateField("website", it) }
            }
        }

        ElevatedCard(shape = RoundedCornerShape(26.dp)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Category", style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.categories) { category ->
                        FilterChip(
                            selected = uiState.contact.category == category,
                            onClick = { viewModel.updateCategory(category) },
                            label = { Text(category.name.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
            }
        }

        Button(modifier = Modifier.fillMaxWidth(), onClick = viewModel::save) {
            Text(if (uiState.isSaving) "Saving..." else "Save Contact")
        }

        uiState.error?.let { Text(text = it, color = colors.error) }
    }
}

@Composable
private fun Field(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(18.dp),
        singleLine = true
    )
}
