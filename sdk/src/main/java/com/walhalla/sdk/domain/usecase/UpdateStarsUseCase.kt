package com.walhalla.sdk.domain.usecase

import com.walhalla.sdk.domain.repository.UserProgressRepository

class UpdateStarsUseCase(private val userProgressRepository: UserProgressRepository) {
    suspend operator fun invoke(level: Int, stars: Int) = userProgressRepository.updateStars(level, stars)
}
