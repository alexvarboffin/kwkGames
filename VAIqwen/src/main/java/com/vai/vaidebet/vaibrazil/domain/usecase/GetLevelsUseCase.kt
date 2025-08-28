package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GetLevelsUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke() = gameRepository.getLevels()
}
