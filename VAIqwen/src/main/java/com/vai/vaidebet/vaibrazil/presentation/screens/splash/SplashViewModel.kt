package com.vai.vaidebet.vaibrazil.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val navigationState: StateFlow<NavigationState> = _navigationState

    init {
        viewModelScope.launch {
            delay(2000) // Show splash screen for 2 seconds
            _navigationState.value = NavigationState.NavigateToHome
        }
    }

    fun onStartClicked() {
        _navigationState.value = NavigationState.NavigateToHome
    }

    sealed class NavigationState {
        object Idle : NavigationState()
        object NavigateToHome : NavigationState()
    }
}