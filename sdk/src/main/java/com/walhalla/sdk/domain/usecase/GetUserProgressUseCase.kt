package com.walhalla.sdk.domain.usecase

import com.walhalla.sdk.domain.repository.UserProgressRepository

class GetUserProgressUseCase(private val userProgressRepository: UserProgressRepository) {
    operator fun invoke() = userProgressRepository.getProgress()
}
