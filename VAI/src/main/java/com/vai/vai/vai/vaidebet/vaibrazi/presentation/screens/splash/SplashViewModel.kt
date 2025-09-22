package com.vai.vai.vaidebet.vaibrazil.presentation.screens.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel : ViewModel() {

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val navigationState = _navigationState.asStateFlow()

    fun onStartClicked() {
        _navigationState.value = NavigationState.NavigateToHome
    }

    sealed class NavigationState {
        object Idle : NavigationState()
        object NavigateToHome : NavigationState()
    }
}
