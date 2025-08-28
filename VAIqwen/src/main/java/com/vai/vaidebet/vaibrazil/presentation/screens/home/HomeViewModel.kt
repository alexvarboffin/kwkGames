package com.vai.vaidebet.vaibrazil.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.di.AppModule
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val levels: List<GameLevel> = emptyList(),
    val error: String? = null,
    val currentPage: Int = 0,
    val pageSize: Int = 9
) {
    val totalPages: Int = if (levels.isNotEmpty()) {
        (levels.size + pageSize - 1) / pageSize
    } else {
        1
    }
    
    val levelsByPage: Map<Int, List<GameLevel>> = levels
        .chunked(pageSize)
        .mapIndexed { index, levelList -> index to levelList }
        .toMap()
}

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadLevels()
    }

    private fun loadLevels() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val levels = AppModule.getLevelsUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    levels = levels,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    levels = emptyList(),
                    error = e.message
                )
            }
        }
    }

    fun nextPage() {
        val currentState = _uiState.value
        if (currentState.currentPage < currentState.totalPages - 1) {
            _uiState.value = currentState.copy(currentPage = currentState.currentPage + 1)
        }
    }

    fun previousPage() {
        val currentState = _uiState.value
        if (currentState.currentPage > 0) {
            _uiState.value = currentState.copy(currentPage = currentState.currentPage - 1)
        }
    }
}