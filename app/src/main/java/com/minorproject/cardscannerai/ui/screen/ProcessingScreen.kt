package com.minorproject.cardscannerai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.minorproject.cardscannerai.ui.viewmodel.ScanViewModel

@Composable
fun ProcessingScreen(
    scanViewModel: ScanViewModel,
    onBack: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val uiState by scanViewModel.uiState.collectAsState()
    val background = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.secondaryContainer.copy(alpha = 0.72f),
            colors.primaryContainer.copy(alpha = 0.7f)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(shape = RoundedCornerShape(30.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (uiState.capturedPageCount > 1) {
                        "Processing ${uiState.capturedPageCount} pages"
                    } else {
                        "Processing 1 page"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.error != null) {
                    Text(
                        text = "❌ Processing Failed",
                        style = MaterialTheme.typography.headlineSmall,
                        color = colors.error
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = uiState.error ?: "Unknown error occurred",
                        color = colors.error
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = onBack) {
                        Text("Back to Scan")
                    }
                } else if (uiState.isProcessing) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Processing card", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Running OCR and AI parsing to structure the contact.",
                        color = colors.onSurfaceVariant
                    )
                    if (uiState.selectedImageUris.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Captured pages: ${uiState.selectedImageUris.size}",
                            color = colors.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                } else {
                    Text(text = "✓ Ready", style = MaterialTheme.typography.headlineSmall, color = colors.primary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Contact processed successfully. Redirecting...",
                        color = colors.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}
