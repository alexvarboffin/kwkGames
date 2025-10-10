package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.Horse
import com.esporte.olimp.yay.domain.repository.HorseRepository

class AddHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horse: Horse): Long {
        return horseRepository.addHorse(horse)
    }
}
