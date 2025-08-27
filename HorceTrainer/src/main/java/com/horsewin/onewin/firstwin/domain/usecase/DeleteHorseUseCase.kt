package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.repository.HorseRepository

class DeleteHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horseId: Long) {
        horseRepository.deleteHorseById(horseId)
    }
}
