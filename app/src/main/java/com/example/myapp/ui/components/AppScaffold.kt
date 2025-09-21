package com.example.myapp.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapp.navigation.NavGraph
import com.example.myapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showTopBar = currentRoute in listOf(Screen.Login.route, Screen.Home.route)
    val showBottomBar = currentRoute == Screen.Home.route
    val topBarTitle = when (currentRoute) {
        Screen.Login.route -> "Login"
        Screen.Home.route -> "Home"
        else -> ""
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
                CenterAlignedTopAppBar(
                    title = { Text(topBarTitle) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = { navController.navigate(Screen.Home.route) },
                        label = { Text("Home") },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { /* TODO: navigate to profile */ },
                        label = { Text("Profile") },
                        icon = { Icon(Icons.Filled.Person, contentDescription = null) }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavGraph(
            navController = navController,
        )
    }
}