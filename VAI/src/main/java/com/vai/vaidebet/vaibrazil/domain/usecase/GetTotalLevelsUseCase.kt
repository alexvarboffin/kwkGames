package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GetTotalLevelsUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(): Int {
        return gameRepository.getTotalLevels()
    }
}
