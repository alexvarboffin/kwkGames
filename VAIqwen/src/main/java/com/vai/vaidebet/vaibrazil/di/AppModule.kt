package com.vai.vaidebet.vaibrazil.di

import com.vai.vaidebet.vaibrazil.data.datasource.LocalDataSource
import com.vai.vaidebet.vaibrazil.data.repository.GameRepositoryImpl
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelsUseCase

object AppModule {
    private val localDataSource = LocalDataSource()
    private val gameRepository: GameRepository = GameRepositoryImpl(localDataSource)
    
    val getLevelsUseCase = GetLevelsUseCase(gameRepository)
    val getLevelUseCase = GetLevelUseCase(gameRepository)
}