package com.horsewin.onewin.firstwin.presentation.health

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horsewin.onewin.firstwin.domain.model.HealthRecord
import com.horsewin.onewin.firstwin.domain.usecase.GetHealthRecordsForHorseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HealthUiState(
    val healthRecords: List<HealthRecord> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HealthViewModel(private val getHealthRecordsForHorseUseCase: GetHealthRecordsForHorseUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(HealthUiState())
    val uiState: StateFlow<HealthUiState> = _uiState.asStateFlow()

    init {
        loadHealthRecords(1L) // TODO: Replace with actual selected horse ID
    }

    fun loadHealthRecords(horseId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                getHealthRecordsForHorseUseCase(horseId).collect { records ->
                    _uiState.value = _uiState.value.copy(healthRecords = records, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load health records: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}
