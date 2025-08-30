package com.walhalla.sdk.domain.usecase

import com.walhalla.sdk.domain.repository.GameRepository

class GetTotalLevelsUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(): Int {
        return gameRepository.getTotalLevels()
    }
}
