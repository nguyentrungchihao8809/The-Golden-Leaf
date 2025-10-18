package com.example.goldenleafapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goldenleafapp.ui.screens.HomeScreen
import com.example.goldenleafapp.ui.screens.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Tạo "bộ điều khiển"

    // NavHost là nơi hiển thị các màn hình
    NavHost(navController = navController, startDestination = "login") {
        // Định nghĩa màn hình "login"
        composable("login") {
            LoginScreen(navController = navController)
        }

        // Định nghĩa màn hình "home"
        composable("home") {
            HomeScreen()
        }
    }
}