package com.walhalla.sdk.domain.usecase

import com.walhalla.sdk.domain.repository.GameRepository

class GetLevelUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(id: Int) = gameRepository.getLevel(id)
}
