package com.makazemi.calculatorBMI.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun toggleGender(newValue:Gender){
        if(newValue!=_uiState.value.gender) {
            _uiState.update { currentState ->
                currentState.copy(gender = if (currentState.gender == Gender.male) Gender.female else Gender.male)
            }
        }
    }

    fun updateHeight(value:Int){
        _uiState.update { currentState ->
            currentState.copy(height = value)
        }
    }

    fun updateWeight(value:Int){
        _uiState.update { currentState ->
            currentState.copy(weight = value)
        }
    }

    fun updateAge(value:Int){
        _uiState.update { currentState ->
            currentState.copy(age = value)
        }
    }

}

data class HomeUiState(
    val gender:Gender=Gender.male,
    val height:Int=183,
    val weight:Int=74,
    val age:Int=34,
)
enum class Gender{
    female,male
}