package com.vai.vaidebet.vaibrazil.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.data.LevelRepository
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = LevelRepository()
    private val getLevelsUseCase = GetLevelsUseCase(repository)
    
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        loadLevels()
    }

    private fun loadLevels() {
        viewModelScope.launch {
            try {
                val levels = getLevelsUseCase()
                _uiState.value = MainUiState.Success(levels)
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val levels: List<GameLevel>) : MainUiState()
    data class Error(val message: String) : MainUiState()
}