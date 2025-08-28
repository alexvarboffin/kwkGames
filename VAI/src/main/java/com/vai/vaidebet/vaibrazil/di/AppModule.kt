package com.vai.vaidebet.vaibrazil.di

import com.vai.vaidebet.vaibrazil.data.datasource.LocalDataSource
import com.vai.vaidebet.vaibrazil.data.repository.GameRepositoryImpl
import com.vai.vaidebet.vaibrazil.data.repository.UserProgressRepositoryImpl
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository
import com.vai.vaidebet.vaibrazil.domain.repository.UserProgressRepository
import com.vai.vaidebet.vaibrazil.domain.usecase.*
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { LocalDataSource(androidContext()) } // Provide Context here
    single<GameRepository> { GameRepositoryImpl(get()) }
    single<UserProgressRepository> { UserProgressRepositoryImpl(androidContext()) }
    factory { GetLevelsUseCase(get()) }
    factory { GetLevelUseCase(get()) }
    factory { GetUserProgressUseCase(get()) }
    factory { UpdateHighestUnlockedLevelUseCase(get()) }
    factory { UpdateStarsUseCase(get()) }

    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { params -> GameViewModel(get(), get(), get(), params.get()) }
}
