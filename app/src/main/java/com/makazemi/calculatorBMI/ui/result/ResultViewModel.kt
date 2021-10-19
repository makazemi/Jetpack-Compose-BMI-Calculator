package com.makazemi.calculatorBMI.ui.result

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makazemi.calculatorBMI.ui.MainDestinations.HEIGHT_ID_KEY
import com.makazemi.calculatorBMI.ui.MainDestinations.WEIGHT_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Math.pow
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class ResultViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val weight: Int? = savedStateHandle.get<Int>(WEIGHT_ID_KEY)
    private val height: Int? = savedStateHandle.get<Int>(HEIGHT_ID_KEY)

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()


    init {
        calculateBMI()
    }

    private fun calculateBMI(){
        if(weight!=null && height!=null) {
            val bmi = weight /pow((height / 100.0),2.0)

            val bmiResult=String.format("%.1f",bmi)
            val resultText=getResult(bmi)
            val interpretation=getInterpretation(bmi)


            _uiState.update {
                it.copy(resultText = resultText,bmiResult = bmiResult,interpretation = interpretation)
            }

        }
    }


    private fun getResult(bmi:Double):String{
        return when {
            bmi >= 25 -> {
                "Overweight";
            }
            bmi > 18.5 -> {
                "Normal";
            }
            else -> {
                "Underweight";
            }
        }
    }

    private fun getInterpretation(bmi:Double):String{
        return when {
            bmi >= 25 -> {
                "You have a higher than normal body weight. Try to exercise more."
            }
            bmi >= 18.5 -> {
                "You have a normal body weight. Good job!"
            }
            else -> {
                "You have a lower than normal body weight. You can eat a bit more."
            }
        }
    }
}

data class ResultUiState(
    val resultText: String = "",
    val bmiResult: String ="",
    val interpretation: String = ""
)
