package com.sosauce.cutecalc.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sosauce.cutecalc.screens.AboutScreen
import com.sosauce.cutecalc.screens.CalculatorUI

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "CalculatorScreen") {
        composable(route = "CalculatorScreen") {
            val viewModel = viewModel<CalcViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            CalculatorUI(navController, state)
        }
        composable(route = "AboutScreen") {
            AboutScreen(navController)
        }
    }

}