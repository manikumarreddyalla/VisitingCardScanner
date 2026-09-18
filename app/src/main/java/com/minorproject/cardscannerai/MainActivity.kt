package com.minorproject.cardscannerai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.minorproject.cardscannerai.ui.CardScannerRoot
import com.minorproject.cardscannerai.ui.theme.CardScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as CardScannerApp
        setContent {
            CardScannerTheme {
                CardScannerRoot(container = app.container)
            }
        }
    }
}
