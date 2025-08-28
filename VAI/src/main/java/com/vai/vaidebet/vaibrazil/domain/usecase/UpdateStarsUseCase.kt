package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.repository.UserProgressRepository

class UpdateStarsUseCase(private val userProgressRepository: UserProgressRepository) {
    suspend operator fun invoke(level: Int, stars: Int) = userProgressRepository.updateStars(level, stars)
}
