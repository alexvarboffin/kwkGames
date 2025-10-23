package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.HealthRecord
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HealthRepository
import kotlinx.coroutines.flow.Flow

class GetHealthRecordsForHorseUseCase(private val healthRepository: HealthRepository) {
    operator fun invoke(horseId: Long): Flow<List<HealthRecord>> {
        return healthRepository.getHealthRecordsForHorse(horseId)
    }
}
