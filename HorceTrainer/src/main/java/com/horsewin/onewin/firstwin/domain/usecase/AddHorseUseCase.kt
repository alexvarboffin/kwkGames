package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.model.Horse
import com.horsewin.onewin.firstwin.domain.repository.HorseRepository

class AddHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horse: Horse): Long {
        return horseRepository.addHorse(horse)
    }
}
