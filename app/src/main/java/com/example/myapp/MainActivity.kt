package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.myapp.core.ui.theme.MyAppTheme
import com.example.myapp.navigation.NavGraph

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install Android 12+ System Splash Screen and hold until session is checked
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.isReady.value
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()
                NavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}
