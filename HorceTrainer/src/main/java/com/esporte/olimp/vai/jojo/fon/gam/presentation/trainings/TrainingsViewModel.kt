package com.esporte.olimp.vai.jojo.fon.gam.presentation.trainings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Training
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetTrainingsForHorseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TrainingsUiState(
    val trainings: List<Training> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class TrainingsViewModel(private val getTrainingsForHorseUseCase: GetTrainingsForHorseUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(TrainingsUiState())
    val uiState: StateFlow<TrainingsUiState> = _uiState.asStateFlow()

    init {
        loadTrainings(1L) // TODO: Replace with actual selected horse ID
    }

    fun loadTrainings(horseId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                getTrainingsForHorseUseCase(horseId).collect { trainings ->
                    _uiState.value = _uiState.value.copy(trainings = trainings, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load trainings: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}
