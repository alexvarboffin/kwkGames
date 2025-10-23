package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Horse
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HorseRepository
import kotlinx.coroutines.flow.Flow

class GetAllHorsesUseCase(private val horseRepository: HorseRepository) {
    operator fun invoke(): Flow<List<Horse>> {
        return horseRepository.getAllHorses()
    }
}
