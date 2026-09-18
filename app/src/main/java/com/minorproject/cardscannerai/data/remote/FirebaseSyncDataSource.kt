package com.minorproject.cardscannerai.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.minorproject.cardscannerai.data.local.entity.ContactEntity
import kotlinx.coroutines.tasks.await

class FirebaseSyncDataSource {
    suspend fun syncContact(contact: ContactEntity) {
        val firestore = FirebaseFirestore.getInstance()
        val payload = mapOf(
            "name" to contact.name,
            "phone" to contact.phone,
            "email" to contact.email,
            "company" to contact.company,
            "designation" to contact.designation,
            "address" to contact.address,
            "website" to contact.website,
            "category" to contact.category,
            "rawText" to contact.rawText,
            "updatedAt" to contact.updatedAt
        )
        firestore.collection("contacts").document(contact.id.toString()).set(payload).await()
    }
}
