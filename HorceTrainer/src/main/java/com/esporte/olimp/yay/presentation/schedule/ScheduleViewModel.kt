package com.esporte.olimp.yay.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esporte.olimp.yay.domain.usecase.GetHealthRecordsForHorseUseCase
import com.esporte.olimp.yay.domain.usecase.GetTrainingsForHorseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date

data class ScheduleUiState(
    val events: List<ScheduleEvent> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ScheduleEvent(
    val date: Date,
    val type: String,
    val description: String
)

class ScheduleViewModel(
    private val getTrainingsForHorseUseCase: GetTrainingsForHorseUseCase,
    private val getHealthRecordsForHorseUseCase: GetHealthRecordsForHorseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        loadSchedule(1L) // TODO: Replace with actual selected horse ID
    }

    fun loadSchedule(horseId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                combine(
                    getTrainingsForHorseUseCase(horseId),
                    getHealthRecordsForHorseUseCase(horseId)
                ) { trainings, healthRecords ->
                    val trainingEvents = trainings.map { training ->
                        ScheduleEvent(training.date, "Training", training.name)
                    }
                    val healthEvents = healthRecords.map { record ->
                        ScheduleEvent(record.date, "Health", record.comment ?: "Health Record")
                    }
                    (trainingEvents + healthEvents).sortedBy { it.date }
                }.collect { events ->
                    _uiState.value = _uiState.value.copy(events = events, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load schedule: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}
