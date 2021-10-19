package com.makazemi.calculatorBMI.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.makazemi.calculatorBMI.ui.theme.BMICalculatorTheme


@Composable
fun BMICalculatorApp(){

    BMICalculatorTheme {

        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()


        Scaffold(
            scaffoldState = scaffoldState,
        ) {
            BMICalculatorNavGraph(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
    }

}