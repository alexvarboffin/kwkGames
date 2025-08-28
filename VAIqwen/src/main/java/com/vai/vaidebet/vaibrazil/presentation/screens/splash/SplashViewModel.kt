package com.vai.vaidebet.vaibrazil.presentation.screens.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class SplashViewModel : ViewModel() {
    sealed class NavigationState {
        object Idle : NavigationState()
        object NavigateToHome : NavigationState()
    }
}