package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GetLevelUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(id: Int): GameLevel {
        return repository.getLevelById(id) ?: throw IllegalArgumentException("Level with id $id not found")
    }
}