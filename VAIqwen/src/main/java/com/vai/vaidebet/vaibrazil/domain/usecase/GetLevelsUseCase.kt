package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.data.LevelRepository
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel

class GetLevelsUseCase(private val repository: LevelRepository) {
    suspend operator fun invoke(): List<GameLevel> {
        return repository.getAllLevels()
    }
}