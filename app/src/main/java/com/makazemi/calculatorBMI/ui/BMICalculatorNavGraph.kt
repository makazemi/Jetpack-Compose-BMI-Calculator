package com.makazemi.calculatorBMI.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.makazemi.calculatorBMI.ui.MainDestinations.HEIGHT_ID_KEY
import com.makazemi.calculatorBMI.ui.MainDestinations.WEIGHT_ID_KEY
import com.makazemi.calculatorBMI.ui.home.HomeScreen
import com.makazemi.calculatorBMI.ui.home.HomeViewModel
import com.makazemi.calculatorBMI.ui.result.ResultScreen
import com.makazemi.calculatorBMI.ui.result.ResultViewModel

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val RESULT_ROUTE = "result"
    const val WEIGHT_ID_KEY = "weightId"
    const val HEIGHT_ID_KEY = "heightId"
}


@Composable
fun BMICalculatorNavGraph(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    val actions: MainActions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.HOME_ROUTE) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                navigateToResult = actions.navigateToResult,
                homeViewModel = homeViewModel
            )
        }
        composable(
            route = "${MainDestinations.RESULT_ROUTE}/{$WEIGHT_ID_KEY}/{$HEIGHT_ID_KEY}",
            arguments = listOf(navArgument(WEIGHT_ID_KEY) { type = NavType.IntType }, navArgument(
                HEIGHT_ID_KEY
            ) { type = NavType.IntType })
        ) { backStackEntry ->
            // ArticleVM obtains the articleId via backStackEntry.arguments from SavedStateHandle
            val resultViewModel = hiltViewModel<ResultViewModel>()
            ResultScreen(
                onBack = actions.upPress,
                resultViewModel = resultViewModel
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val navigateToResult: (Int,Int) -> Unit = { weight: Int,height:Int ->
        navController.navigate("${MainDestinations.RESULT_ROUTE}/$weight/$height")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}