package com.vai.vaidebet.vaibrazil.di

import com.vai.vaidebet.vaibrazil.data.LevelRepository
import com.vai.vaidebet.vaibrazil.data.repository.GameRepositoryImpl
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelsUseCase
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel
import org.koin.dsl.module

val appModule = module {
    // Репозитории
    single<GameRepository> { GameRepositoryImpl() }
    single { LevelRepository() }
    
    // UseCases
    single { GetLevelsUseCase(get()) }
    single { GetLevelUseCase(get()) }
    
    // ViewModels
    factory { HomeViewModel(get()) }
    factory { (levelId: Int) -> GameViewModel(levelId, get()) }
}