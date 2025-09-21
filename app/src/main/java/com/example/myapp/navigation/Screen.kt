package com.example.myapp.navigation


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
}