package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.repository.HorseRepository

class DeleteHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horseId: Long) {
        horseRepository.deleteHorseById(horseId)
    }
}
