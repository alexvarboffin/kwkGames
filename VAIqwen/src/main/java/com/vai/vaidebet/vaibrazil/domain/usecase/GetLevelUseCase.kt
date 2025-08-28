package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.data.LevelRepository
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel

class GetLevelUseCase(private val repository: LevelRepository) {
    suspend operator fun invoke(id: Int): GameLevel? {
        return repository.getLevelById(id)
    }
}