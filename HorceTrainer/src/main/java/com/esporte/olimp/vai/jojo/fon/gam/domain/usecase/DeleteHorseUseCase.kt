package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HorseRepository

class DeleteHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horseId: Long) {
        horseRepository.deleteHorseById(horseId)
    }
}
