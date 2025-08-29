package com.vai.vaidebet.vaibrazil.data.repository

import com.vai.vaidebet.vaibrazil.data.datasource.LocalDataSource
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameRepositoryImpl(private val localDataSource: LocalDataSource) : GameRepository {
    override fun getLevels(): Flow<List<GameLevel>> = flow {
        emit(localDataSource.getLevels())
    }

    override suspend fun getLevel(id: Int): GameLevel? {
        return localDataSource.getLevels().find { it.id == id }
    }

    override suspend fun getTotalLevels(): Int {
        return localDataSource.getTotalLevels()
    }
}