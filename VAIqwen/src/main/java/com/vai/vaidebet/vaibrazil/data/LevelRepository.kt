package com.vai.vaidebet.vaibrazil.data

import com.vai.vaidebet.vaibrazil.data.repository.GameRepositoryImpl

class LevelRepository {
    private val repository = GameRepositoryImpl()
    
    suspend fun getAllLevels() = repository.getLevels()
    suspend fun getLevelById(id: Int) = repository.getLevelById(id)
}