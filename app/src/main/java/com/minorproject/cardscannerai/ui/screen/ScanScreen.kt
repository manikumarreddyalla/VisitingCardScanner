package com.minorproject.cardscannerai.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.minorproject.cardscannerai.ui.viewmodel.ScanViewModel

@Composable
fun ScanScreen(
    scanViewModel: ScanViewModel,
    onProcessing: () -> Unit,
    onOpenContacts: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val context = LocalContext.current
    val uiState by scanViewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme
    val background = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.primaryContainer.copy(alpha = 0.38f),
            colors.tertiaryContainer.copy(alpha = 0.32f)
        )
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            scanViewModel.onImagesCaptured(listOf(it))
        }
    }

    val multipleImagesLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            scanViewModel.onImagesCaptured(uris)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let { b ->
            // save bitmap to cache and pass as Uri to viewModel
            val file = java.io.File(context.cacheDir, "scan_${System.currentTimeMillis()}.jpg")
            try {
                val fos = java.io.FileOutputStream(file)
                b.compress(android.graphics.Bitmap.CompressFormat.JPEG, 90, fos)
                fos.flush()
                fos.close()
                val uri = Uri.fromFile(file)
                scanViewModel.onImagesCaptured(listOf(uri))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "CardScanner AI", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "A cleaner way to scan business cards, review details, and save contacts.",
                color = colors.onSurfaceVariant
            )
        }

        ElevatedCard(shape = RoundedCornerShape(28.dp)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Pill(text = "OCR")
                    Pill(text = "NLP")
                    Pill(text = "Sync")
                }

                Text(text = "Scan a business card in one tap.", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Import from the camera or gallery. The app extracts the contact and takes you straight to review.",
                    color = colors.onSurfaceVariant
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isProcessing,
                    onClick = {
                        // Open gallery to select image(s)
                        multipleImagesLauncher.launch("image/*")
                    }
                ) {
                    Text(if (uiState.isProcessing) "Processing..." else "Capture / Import Card")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { cameraLauncher.launch(null) }, enabled = !uiState.isProcessing) {
                        Text("Open Camera")
                    }

                    if (uiState.selectedImageUris.isNotEmpty()) {
                        Button(onClick = {
                            onProcessing()
                            scanViewModel.processCard(uiState.selectedImageUris)
                        }, enabled = !uiState.isProcessing) {
                            Text("Next")
                        }
                    }
                }

                if (uiState.selectedImageUri != null) {
                    Text(
                        text = if (uiState.capturedPageCount > 1) {
                            "${uiState.capturedPageCount} pages captured and ready for processing."
                        } else {
                            "Last capture ready for processing."
                        },
                        color = colors.primary
                    )
                }

                uiState.error?.let {
                    Text(text = it, color = colors.error)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ElevatedCard(modifier = Modifier.fillMaxWidth(0.5f), shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Contacts", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Open your saved contact library.", color = colors.onSurfaceVariant)
                    TextButton(onClick = onOpenContacts) { Text("View") }
                }
            }
            ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Settings", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Adjust sync and app preferences.", color = colors.onSurfaceVariant)
                    TextButton(onClick = onOpenSettings) { Text("Open") }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun Pill(text: String) {
    val colors = MaterialTheme.colorScheme
    Surface(
        color = colors.secondaryContainer.copy(alpha = 0.55f),
        shape = RoundedCornerShape(999.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
