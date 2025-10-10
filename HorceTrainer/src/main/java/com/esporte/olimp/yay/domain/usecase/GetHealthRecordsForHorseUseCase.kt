package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.HealthRecord
import com.esporte.olimp.yay.domain.repository.HealthRepository
import kotlinx.coroutines.flow.Flow

class GetHealthRecordsForHorseUseCase(private val healthRepository: HealthRepository) {
    operator fun invoke(horseId: Long): Flow<List<HealthRecord>> {
        return healthRepository.getHealthRecordsForHorse(horseId)
    }
}
