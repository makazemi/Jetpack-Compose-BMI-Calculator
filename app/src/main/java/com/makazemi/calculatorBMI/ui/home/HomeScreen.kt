package com.makazemi.calculatorBMI.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makazemi.calculatorBMI.R
import com.makazemi.calculatorBMI.ui.components.InsetAwareTopAppBar
import com.makazemi.calculatorBMI.ui.theme.BMICalculatorTheme


@Composable
fun HomeScreen(
    navigateToResult: (Int, Int) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    homeViewModel: HomeViewModel
) {
    val uiState: HomeUiState by homeViewModel.uiState.collectAsState()

    HomeScreen(
        gender = uiState.gender,
        height = uiState.height,
        weight = uiState.weight,
        age = uiState.age,
        onToggleGender = homeViewModel::toggleGender,
        updateHeight = homeViewModel::updateHeight,
        updateWeight = homeViewModel::updateWeight,
        updateAge = homeViewModel::updateAge,
        navigateToResult = navigateToResult,
        scaffoldState = scaffoldState
    )


}


@Composable
fun HomeScreen(
    gender: Gender,
    height: Int,
    weight: Int,
    age: Int,
    onToggleGender: (gender:Gender) -> Unit,
    updateHeight: (Int) -> Unit,
    updateWeight: (Int) -> Unit,
    updateAge: (Int) -> Unit,
    navigateToResult: (Int, Int) -> Unit,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scaffoldState,
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
            gender = gender, height = height,
            weight = weight, age = age, onToggleGender = onToggleGender,
            updateHeight = updateHeight, updateWeight = updateWeight, updateAge = updateAge,
            navigateToResult = navigateToResult
        )
    }
}

@Composable
fun LoadingContent(
    gender: Gender,
    height: Int,
    weight: Int,
    age: Int,
    onToggleGender: (gender:Gender) -> Unit,
    updateHeight: (Int) -> Unit,
    updateWeight: (Int) -> Unit,
    updateAge: (Int) -> Unit,
    navigateToResult: (Int, Int) -> Unit
) {

    Column {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                GenderCards(gender, onToggleGender)
            }
            item {
                HeightCard(height = height, updateHeight = updateHeight)
            }
            item {
                WeightAndAgeCards(
                    weight = weight,
                    updateWeight = updateWeight,
                    age = age,
                    updateAge = updateAge
                )
            }
        }
        Button(
            onClick = {
                navigateToResult(weight, height)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "CALCULATE YOUR BMI",
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }
    }

}

@Composable
fun WeightAndAgeCards(
    weight: Int,
    updateWeight: (Int) -> Unit,
    age: Int,
    updateAge: (Int) -> Unit
) {
    Row(modifier = Modifier.padding(20.dp)) {
        IncrementDecrementCard(Modifier.weight(0.5f), "WEIGHT", weight, updateWeight)
        Spacer(modifier = Modifier.padding(5.dp))
        IncrementDecrementCard(Modifier.weight(0.5f), "AGE", age, updateAge)

    }
}

@Composable
fun IncrementDecrementCard(
    modifier: Modifier,
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .background(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colors.background
            )
            .padding(10.dp)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.background
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
            )
            Row(modifier = Modifier.padding(top = 20.dp)) {
                OutlinedButton(
                    onClick = { onValueChange(value + 1) },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(50.dp),  //avoid the oval shape
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "content description",
                        tint = MaterialTheme.colors.secondaryVariant,
                    )
                }

                OutlinedButton(
                    onClick = { onValueChange(value - 1) },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(50.dp),  //avoid the oval shape
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "content description",
                        tint = MaterialTheme.colors.secondaryVariant,
                    )
                }
            }

        }
    }
}

@Composable
fun HeightCard(height: Int, updateHeight: (Int) -> Unit) {
    var sliderPosition by remember { mutableStateOf(0f) }


    Surface(
        modifier = Modifier
            .padding(20.dp)
            .background(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colors.background
            )
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.background
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = "HEIGHT",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.secondaryVariant
            )
            Row {
                Text(
                    text = height.toString(),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.alignByBaseline()
                )
                Text(
                    text = "cm",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.alignByBaseline()
                )
            }
            Slider(
                value = height.toFloat(),
                onValueChange = { updateHeight(it.toInt())},
                valueRange= 0f..200f,
               // steps= 1,
                modifier = Modifier.padding(top = 20.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = MaterialTheme.colors.secondaryVariant
                )
            )


        }
    }


}


@Composable
fun GenderCards(gender: Gender, onToggleGender: (gender:Gender) -> Unit) {
    Row(modifier = Modifier.padding(20.dp)) {
        GenderCard(
            genderTitle = "MALE",
            icon = Icons.Filled.Male,
            modifier = Modifier.weight(0.5f),
            onToggleGender = onToggleGender,
            gender=Gender.male,
            backgroundColor = if (gender == Gender.male) MaterialTheme.colors.onPrimary else MaterialTheme.colors.secondaryVariant
        )

        Spacer(modifier = Modifier.padding(5.dp))

        GenderCard(
            genderTitle = "FEMALE",
            icon = Icons.Filled.Female,
            modifier = Modifier.weight(0.5f),
            onToggleGender = onToggleGender,
            gender=Gender.female,
            backgroundColor = if (gender == Gender.female) MaterialTheme.colors.onPrimary else MaterialTheme.colors.secondaryVariant
        )

    }
}


@Composable
fun GenderCard(
    genderTitle: String,
    icon: ImageVector,
    modifier: Modifier,
    onToggleGender: (gender:Gender) -> Unit,
    gender: Gender,
    backgroundColor: Color,
) {
    Surface(
        modifier = Modifier
            .background(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colors.background
            )
            .padding(top = 10.dp, bottom = 10.dp)
            .then(modifier)
            .clickable(true,onClick = {onToggleGender(gender)}),
       // onClick = { onToggleGender(gender) }

    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.background
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(R.string.icon_dsc),
                tint = backgroundColor,
                modifier = Modifier.size(60.dp)
            )
            Text(
                text = genderTitle,
                color = backgroundColor,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

    }
}



@Preview("HomeScreen")
@Composable
fun PreviewHomeScreen() {
    BMICalculatorTheme {
        HomeScreen(
            gender = Gender.female,
            height = 100,
            weight = 65,
            age=23,
            onToggleGender = {},
            updateAge = {},
            updateWeight = {},
            updateHeight = {},
            navigateToResult = {i,j->},
            scaffoldState = rememberScaffoldState()
        )
    }
}