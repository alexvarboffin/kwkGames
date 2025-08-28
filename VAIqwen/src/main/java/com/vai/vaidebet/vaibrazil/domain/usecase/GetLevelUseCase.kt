package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GetLevelUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(id: Int) = gameRepository.getLevel(id)
}
