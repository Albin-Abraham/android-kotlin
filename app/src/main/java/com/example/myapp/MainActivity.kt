package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.components.AppScaffold
import com.example.myapp.ui.theme.MyAppTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                AppScaffold(navController = navController)
            }
        }
    }
}


