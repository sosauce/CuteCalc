package com.sosauce.cutecalc

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav(updateTheme: (UserTheme) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "CalculatorScreen") {
         composable(route = "CalculatorScreen") {
             CalculatorUI(navController)
         }
        composable(route = "AboutScreen") {
            AboutScreen(navController, viewModel = viewModel())
        }
        composable(route = "SettingsScreen") {
            SettingsScreen(navController, updateTheme)
        }
    }
    
}