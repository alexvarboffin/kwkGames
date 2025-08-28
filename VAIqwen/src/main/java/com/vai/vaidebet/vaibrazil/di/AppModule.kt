package com.vai.vaidebet.vaibrazil.di

import com.vai.vaidebet.vaibrazil.data.datasource.LocalDataSource
import com.vai.vaidebet.vaibrazil.data.repository.GameRepositoryImpl
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelsUseCase
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { LocalDataSource() }
    single<GameRepository> { GameRepositoryImpl(get()) }
    factory { GetLevelsUseCase(get()) }
    factory { GetLevelUseCase(get()) }

    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { params -> GameViewModel(get(), params.get()) }
}
