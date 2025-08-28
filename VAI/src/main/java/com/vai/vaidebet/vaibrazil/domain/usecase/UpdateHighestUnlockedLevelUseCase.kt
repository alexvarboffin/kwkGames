package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.repository.UserProgressRepository

class UpdateHighestUnlockedLevelUseCase(private val userProgressRepository: UserProgressRepository) {
    suspend operator fun invoke(level: Int) = userProgressRepository.updateHighestUnlockedLevel(level)
}
