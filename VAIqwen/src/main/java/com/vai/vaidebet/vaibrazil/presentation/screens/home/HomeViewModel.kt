package com.vai.vaidebet.vaibrazil.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getLevelsUseCase: GetLevelsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadLevels()
    }

    private fun loadLevels() {
        viewModelScope.launch {
            try {
                val levels = getLevelsUseCase()
                _uiState.value = HomeUiState.Success(levels)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val levels: List<GameLevel>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}