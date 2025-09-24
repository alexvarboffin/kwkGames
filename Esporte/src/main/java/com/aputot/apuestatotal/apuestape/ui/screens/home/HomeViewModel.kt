package com.aputot.apuestatotal.apuestape.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walhalla.sdk.domain.model.GameLevel
import com.walhalla.sdk.domain.model.UserProgress
import com.walhalla.sdk.domain.usecase.GetLevelsUseCase
import com.walhalla.sdk.domain.usecase.GetUserProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getLevelsUseCase: GetLevelsUseCase,
    private val getUserProgressUseCase: GetUserProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var currentPage = 0
    private var totalPages = 0

    init {
        loadLevelsAndProgress()
    }

    private fun loadLevelsAndProgress() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val levels = getLevelsUseCase()
                val progress = getUserProgressUseCase()

                levels.combine(progress) { levelsData, progressData ->
                    val levelsByPage = levelsData.chunked(16)
                    totalPages = levelsByPage.size
                    HomeUiState.Success(levelsByPage, currentPage, totalPages, progressData)
                }.collect { state ->
                    _uiState.value = state
                }
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
        val totalPages: Int,
        val userProgress: UserProgress
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
