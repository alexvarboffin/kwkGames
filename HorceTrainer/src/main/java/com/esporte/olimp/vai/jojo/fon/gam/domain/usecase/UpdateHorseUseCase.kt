package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Horse
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HorseRepository

class UpdateHorseUseCase(private val horseRepository: HorseRepository) {
    suspend operator fun invoke(horse: Horse) {
        horseRepository.updateHorse(horse)
    }
}
