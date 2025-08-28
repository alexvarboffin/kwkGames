package com.vai.vaidebet.vaibrazil.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getLevelsUseCase: GetLevelsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var currentPage = 0
    private var totalPages = 0

    init {
        loadLevels()
    }

    private fun loadLevels() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val levels = getLevelsUseCase()
                val levelsByPage = levels.chunked(16)
                totalPages = levelsByPage.size
                _uiState.value = HomeUiState.Success(levelsByPage, currentPage, totalPages)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                _uiState.value = currentState.copy(currentPage = currentPage)
            }
        }
    }

    fun previousPage() {
        if (currentPage > 0) {
            currentPage--
            val currentState = _uiState.value
            if (currentState is HomeUiState.Success) {
                _uiState.value = currentState.copy(currentPage = currentPage)
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val levelsByPage: List<List<GameLevel>>,
        val currentPage: Int,
        val totalPages: Int
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}