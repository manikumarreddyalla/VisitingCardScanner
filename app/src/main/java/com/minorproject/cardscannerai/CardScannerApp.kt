package com.minorproject.cardscannerai

import android.app.Application
import com.minorproject.cardscannerai.di.AppContainer

class CardScannerApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
