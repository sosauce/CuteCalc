package com.sosauce.cutecalc.logic.navigation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sosauce.cutecalc.ecosys.EcosystemViewModel
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.screens.CalculatorUI
import com.sosauce.cutecalc.screens.HistoryScreen
import com.sosauce.cutecalc.screens.SettingsScreen
import com.sosauce.cutecalc.ui.theme.GlobalFont
import com.sosauce.cutecalc.utils.CUTE_MUSIC

@Composable
fun Nav(
    ecosystemViewModel: EcosystemViewModel,
    historyViewModel: HistoryViewModel
) {
    val navController = rememberNavController()
    val historyState by historyViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screens.Main
    ) {
        composable<Screens.Main> {
            val viewModel = viewModel<CalcViewModel>()
            CalculatorUI(
                viewModel = viewModel,
                historyViewModel = historyViewModel,
                historyState = historyState,
                onNavigateUp = navController::navigateUp,
                onNavigate = { navController.navigate(it) },
                cmSongTitle = {
                    if (ecosystemViewModel.currentlyPlaying != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(11.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .clickable {
                                    Intent(Intent.ACTION_MAIN).also {
                                        it.`package` = CUTE_MUSIC
                                        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                                        context.startActivity(it)
                                    }
                                }
                        ) {
                            Text(
                                text = ecosystemViewModel.currentlyPlaying!!,
                                fontFamily = GlobalFont,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .basicMarquee()
                            )
                        }
                    }
                }
            )
        }
        composable<Screens.Settings> {
            SettingsScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = { navController.navigate(it) }
            )
        }
        composable<Screens.History> {
            HistoryScreen(
                state = historyState,
                onEvents = historyViewModel::onEvent,
                onNavigateUp = navController::navigateUp,
                onNavigate = { navController.navigate(it) },
            )
        }
    }

}