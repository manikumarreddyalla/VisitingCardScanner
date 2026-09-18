package com.minorproject.cardscannerai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.minorproject.cardscannerai.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = MaterialTheme.colorScheme
    val background = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.surfaceVariant.copy(alpha = 0.6f),
            colors.primaryContainer.copy(alpha = 0.45f)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Settings", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Tune sync and app behavior", color = colors.onSurfaceVariant)
            }
            TextButton(onClick = onBack) {
                Text("Back")
            }
        }

        ElevatedCard(shape = RoundedCornerShape(28.dp)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Sync")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Cloud Sync", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Keep scanned contacts available across devices when enabled.",
                            color = colors.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.cloudSyncEnabled,
                        onCheckedChange = viewModel::setCloudSync
                    )
                }
            }
        }

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = colors.secondaryContainer.copy(alpha = 0.45f),
            tonalElevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(text = "Privacy-first by default", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Local processing stays available even when cloud sync is disabled.")
            }
        }
    }
}
