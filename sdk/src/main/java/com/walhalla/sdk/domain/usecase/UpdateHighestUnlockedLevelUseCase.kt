package com.walhalla.sdk.domain.usecase

import com.walhalla.sdk.domain.repository.UserProgressRepository

class UpdateHighestUnlockedLevelUseCase(private val userProgressRepository: UserProgressRepository) {
    suspend operator fun invoke(level: Int) = userProgressRepository.updateHighestUnlockedLevel(level)
}
