package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GetLevelsUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(): List<GameLevel> {
        return repository.getAllLevels()
    }
}