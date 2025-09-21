package com.example.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapp.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = Screen.Splash.route) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { HomeScreen() }

    }
}
