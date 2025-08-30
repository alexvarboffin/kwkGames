package com.walhalla.sdk.domain.usecase

import com.walhalla.sdk.domain.repository.GameRepository

class GetLevelsUseCase(private val gameRepository: GameRepository) {
    operator fun invoke() = gameRepository.getLevels()
}