package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.model.Horse
import com.horsewin.onewin.firstwin.domain.repository.HorseRepository
import kotlinx.coroutines.flow.Flow

class GetAllHorsesUseCase(private val horseRepository: HorseRepository) {
    operator fun invoke(): Flow<List<Horse>> {
        return horseRepository.getAllHorses()
    }
}
