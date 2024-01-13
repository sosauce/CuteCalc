package com.sosauce.cutecalc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "CalculatorScreen") {
        composable(route = "CalculatorScreen") {
            val viewModel = viewModel<CalcViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            CalculatorUI(navController, state, viewModel::handleAction)
        }
        composable(route = "AboutScreen") {
            AboutScreen(navController)
        }
    }
    
}