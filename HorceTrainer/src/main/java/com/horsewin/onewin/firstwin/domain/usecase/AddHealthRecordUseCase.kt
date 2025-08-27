package com.horsewin.onewin.firstwin.domain.usecase

import com.horsewin.onewin.firstwin.domain.model.HealthRecord
import com.horsewin.onewin.firstwin.domain.repository.HealthRepository

class AddHealthRecordUseCase(private val healthRepository: HealthRepository) {
    suspend operator fun invoke(record: HealthRecord): Long {
        return healthRepository.addHealthRecord(record)
    }
}
