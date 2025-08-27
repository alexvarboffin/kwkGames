package com.horsewin.onewin.firstwin.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horsewin.onewin.firstwin.data.SettingsDataStore
import com.horsewin.onewin.firstwin.domain.model.Horse
import com.horsewin.onewin.firstwin.domain.usecase.GetAllHorsesUseCase
import com.horsewin.onewin.firstwin.domain.usecase.GetHorseByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ProfileUiState(
    val horse: Horse? = null,
    val horses: List<Horse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProfileViewModel(
    private val getHorseByIdUseCase: GetHorseByIdUseCase,
    private val getAllHorsesUseCase: GetAllHorsesUseCase,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllHorsesUseCase().collect { horses ->
                _uiState.value = _uiState.value.copy(horses = horses)
                if (horses.isNotEmpty()) {
                    val selectedHorseId = settingsDataStore.selectedHorseId.first()
                    if (selectedHorseId != null) {
                        loadHorse(selectedHorseId)
                    } else {
                        loadHorse(horses.first().id)
                    }
                }
            }
        }
    }

    fun loadHorse(horseId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                getHorseByIdUseCase(horseId).collect { horse ->
                    _uiState.value = _uiState.value.copy(horse = horse, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load horse: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun selectHorse(horseId: Long) {
        viewModelScope.launch {
            settingsDataStore.setSelectedHorseId(horseId)
            loadHorse(horseId)
        }
    }
}

