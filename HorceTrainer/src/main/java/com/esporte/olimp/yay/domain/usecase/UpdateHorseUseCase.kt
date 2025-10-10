package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.Horse
import com.esporte.olimp.yay.domain.repository.HorseRepository

class UpdateHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horse: Horse) {
        horseRepository.updateHorse(horse)
    }
}
