# CardScanner AI (Android)

Lightweight, offline-first visiting card scanner built with Kotlin + Jetpack Compose.

## Stack
- UI: Jetpack Compose
- Architecture: MVVM + Clean Architecture + Repository pattern
- OCR: Google ML Kit (on-device)
- NLP: On-device heuristic entity extraction
- Local storage: Room
- Optional sync: Firebase Firestore

## Lightweight & Offline

The app is designed to be lightweight and offline-first:
- On-device OCR (ML Kit) and simple heuristic/AI post-processing.
- Minimal dependencies; Room stores scanned cards locally as compact JSON.
- Export: users can export any scanned card to vCard format for sharing or import.

## Local access & Export
- All scanned cards are stored locally in `Room` and accessible via the app UI.
- Use the export button on a scanned-card detail screen to convert fields to a vCard (.vcf) file.
- vCard generation preserves common fields: name, organization, title, email, phone, address, url.

## Features
- Document scanner flow with auto edge detection & crop (ML Kit Document Scanner)
- OCR text extraction
- NLP parsing into structured contact fields
- Confidence scores for each extracted field
- Smart categorization (Business/Vendor/Client/Personal/Unknown)
- Duplicate detection (phone/email)
- Edit-before-save screen
- Local offline save (Room)
- Optional cloud sync toggle
- Save to phone contacts
- Share as vCard
- Search contacts
- Dark mode support
- Multi-language OCR dependencies included

## Project Structure
- `app/src/main/java/com/minorproject/cardscannerai/ai` OCR + NLP + preprocessing
- `app/src/main/java/com/minorproject/cardscannerai/data` Room, preferences, remote sync, repositories
- `app/src/main/java/com/minorproject/cardscannerai/domain` models + repository interfaces
- `app/src/main/java/com/minorproject/cardscannerai/ui` Compose screens, navigation, viewmodels
- `app/src/main/assets/sample_cards` sample test card images

## Build & Run
1. Open project in Android Studio (JDK 17).
2. Sync Gradle.
3. Run on Android 8.0+ device/emulator.

## Firebase (Optional)
Cloud sync is disabled by default. To enable full Firebase functionality:
1. Create a Firebase Android app for `com.minorproject.cardscannerai`.
2. Add `google-services.json` to `app/`.
3. Re-enable plugin `id("com.google.gms.google-services")` in `app/build.gradle.kts` if desired.
4. Toggle Cloud Sync in Settings.

Without Firebase setup, app remains fully functional offline.