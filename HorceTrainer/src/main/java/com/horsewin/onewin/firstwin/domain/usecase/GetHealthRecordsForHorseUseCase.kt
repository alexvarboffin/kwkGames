package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.model.HealthRecord
import com.horsewin.onewin.firstwin.domain.repository.HealthRepository
import kotlinx.coroutines.flow.Flow

class GetHealthRecordsForHorseUseCase(private val healthRepository: HealthRepository) {
    operator fun invoke(horseId: Long): Flow<List<HealthRecord>> {
        return healthRepository.getHealthRecordsForHorse(horseId)
    }
}
