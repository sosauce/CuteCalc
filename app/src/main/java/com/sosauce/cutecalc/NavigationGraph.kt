package com.sosauce.cutecalc

import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "CalculatorScreen") {
         composable(route = "CalculatorScreen") {
             CalculatorUI(navController)
         }
        composable(route = "AboutScreen") {
            AboutScreen(navController)
        }
        composable(route = "SettingsScreen") {
            SettingsScreen(navController)
        }
    }
    
}