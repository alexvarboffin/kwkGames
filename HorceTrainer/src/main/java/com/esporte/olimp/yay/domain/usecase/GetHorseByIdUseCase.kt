package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.Horse
import com.esporte.olimp.yay.domain.repository.HorseRepository
import kotlinx.coroutines.flow.Flow

class GetHorseByIdUseCase(private val horseRepository: HorseRepository) {
    operator fun invoke(horseId: Long): Flow<Horse?> {
        return horseRepository.getHorseById(horseId)
    }
}
