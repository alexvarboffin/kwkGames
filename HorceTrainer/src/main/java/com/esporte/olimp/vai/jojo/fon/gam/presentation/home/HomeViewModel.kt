package com.esporte.olimp.vai.jojo.fon.gam.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esporte.olimp.vai.jojo.fon.gam.domain.model.HealthRecord
import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Training
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetHealthRecordsForHorseUseCase
import com.esporte.olimp.vai.jojo.fon.gam.domain.usecase.GetTrainingsForHorseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class HomeUiState(
    val latestTraining: Training? = null,
    val latestHealthRecord: HealthRecord? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val getTrainingsForHorseUseCase: GetTrainingsForHorseUseCase,
    private val getHealthRecordsForHorseUseCase: GetHealthRecordsForHorseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadLatestData(1L) // TODO: Replace with actual selected horse ID
    }

    fun loadLatestData(horseId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                combine(
                    getTrainingsForHorseUseCase(horseId),
                    getHealthRecordsForHorseUseCase(horseId)
                ) { trainings, healthRecords ->
                    val latestTraining = trainings.maxByOrNull { it.date }
                    val latestHealthRecord = healthRecords.maxByOrNull { it.date }
                    _uiState.value = _uiState.value.copy(
                        latestTraining = latestTraining,
                        latestHealthRecord = latestHealthRecord,
                        isLoading = false
                    )
                }.collect {}
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load data: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}
