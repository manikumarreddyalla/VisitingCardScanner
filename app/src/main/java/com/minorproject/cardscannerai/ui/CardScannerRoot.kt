package com.minorproject.cardscannerai.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.minorproject.cardscannerai.di.AppContainer
import com.minorproject.cardscannerai.ui.screen.ContactDetailScreen
import com.minorproject.cardscannerai.ui.screen.ContactsScreen
import com.minorproject.cardscannerai.ui.screen.EditContactScreen
import com.minorproject.cardscannerai.ui.screen.ProcessingScreen
import com.minorproject.cardscannerai.ui.screen.ScanScreen
import com.minorproject.cardscannerai.ui.screen.SettingsScreen
import com.minorproject.cardscannerai.ui.screen.SplashScreen
import com.minorproject.cardscannerai.ui.viewmodel.AppViewModelFactory
import com.minorproject.cardscannerai.ui.viewmodel.ContactsViewModel
import com.minorproject.cardscannerai.ui.viewmodel.EditContactViewModel
import com.minorproject.cardscannerai.ui.viewmodel.ScanViewModel
import com.minorproject.cardscannerai.ui.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay

@Composable
fun CardScannerRoot(container: AppContainer) {
    val navController = rememberNavController()
    val factory = AppViewModelFactory(container)

    val scanViewModel: ScanViewModel = viewModel(factory = factory)
    val editViewModel: EditContactViewModel = viewModel(factory = factory)
    val contactsViewModel: ContactsViewModel = viewModel(factory = factory)
    val settingsViewModel: SettingsViewModel = viewModel(factory = factory)

    val parsedContact by scanViewModel.parsedContact.collectAsState()

    LaunchedEffect(parsedContact) {
        val contact = parsedContact ?: return@LaunchedEffect
        editViewModel.setContact(contact)
        navController.navigate(AppRoutes.EditContact) {
            popUpTo(AppRoutes.Processing) { inclusive = true }
        }
        delay(300)
        scanViewModel.clearParsedContact()
    }

    NavHost(navController = navController, startDestination = AppRoutes.Splash) {
        composable(AppRoutes.Splash) {
            SplashScreen(onTimeout = {
                navController.navigate(AppRoutes.Scan) {
                    popUpTo(AppRoutes.Splash) { inclusive = true }
                }
            })
        }
        composable(AppRoutes.Scan) {
            ScanScreen(
                scanViewModel = scanViewModel,
                onProcessing = { navController.navigate(AppRoutes.Processing) },
                onOpenContacts = { navController.navigate(AppRoutes.Contacts) },
                onOpenSettings = { navController.navigate(AppRoutes.Settings) }
            )
        }
        composable(AppRoutes.Processing) {
            ProcessingScreen(
                scanViewModel = scanViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoutes.EditContact) {
            EditContactScreen(
                viewModel = editViewModel,
                onSaved = {
                    navController.navigate(AppRoutes.Contacts) {
                        popUpTo(AppRoutes.Scan)
                    }
                }
            )
        }
        composable(AppRoutes.Contacts) {
            ContactsScreen(
                viewModel = contactsViewModel,
                onBack = { navController.popBackStack() },
                onOpenDetail = { id -> navController.navigate("${AppRoutes.ContactDetail}/$id") }
            )
        }
        composable(
            route = "${AppRoutes.ContactDetail}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            ContactDetailScreen(
                id = id,
                contactsViewModel = contactsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoutes.Settings) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
