package com.vai.vaidebet.vaibrazil.domain.usecase

import com.vai.vaidebet.vaibrazil.domain.repository.UserProgressRepository

class GetUserProgressUseCase(private val userProgressRepository: UserProgressRepository) {
    operator fun invoke() = userProgressRepository.getProgress()
}
