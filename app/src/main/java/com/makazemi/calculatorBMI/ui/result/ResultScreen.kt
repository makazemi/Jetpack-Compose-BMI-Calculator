package com.makazemi.calculatorBMI.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makazemi.calculatorBMI.R
import com.makazemi.calculatorBMI.ui.components.InsetAwareTopAppBar

@Composable
fun ResultScreen(
    onBack: () -> Unit,
    resultViewModel: ResultViewModel
) {

    val uiState: ResultUiState by resultViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            InsetAwareTopAppBar(
                title = {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,

                        )
                },
                backgroundColor = MaterialTheme.colors.secondary,
                elevation = 4.dp
            )
        },
        backgroundColor = MaterialTheme.colors.secondary

    ) { innerPadding ->
        LoadingContent(
            bmiResult = uiState.bmiResult,
            resultText = uiState.resultText,
            interpretation = uiState.interpretation,
            onBack = onBack
        )
    }

}

@Composable
fun LoadingContent(
    bmiResult: String,
    resultText: String,
    interpretation: String,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.secondary)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Your Result",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(20.dp)
        )

        Surface(
            modifier = Modifier
                .padding(20.dp)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.small
                )
                .padding(30.dp)
        ) {

            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.background
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    text = resultText,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Green,
                    modifier = Modifier.padding(20.dp)
                )

                Text(
                    text = bmiResult,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Normal BMI Range:",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(25.dp)
                )
                Text(
                    text = "18.5 - 25 kg/m2",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(5.dp)
                )

                Text(
                    text = interpretation,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }

        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "RE-CALCULATE YOUR BMI",
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }

    }

}