package com.minorproject.cardscannerai.domain.repository

import android.net.Uri
import com.minorproject.cardscannerai.domain.model.Contact

interface CardScanRepository {
    suspend fun processCardImage(uri: Uri): Contact

    suspend fun processCardImages(imageUris: List<Uri>): Contact
}
