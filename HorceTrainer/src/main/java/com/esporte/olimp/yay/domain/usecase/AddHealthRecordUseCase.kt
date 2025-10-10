package com.esporte.olimp.yay.domain.usecase

import com.esporte.olimp.yay.domain.model.HealthRecord
import com.esporte.olimp.yay.domain.repository.HealthRepository

class AddHealthRecordUseCase(private val healthRepository: HealthRepository) {
    suspend operator fun invoke(record: HealthRecord): Long {
        return healthRepository.addHealthRecord(record)
    }
}
