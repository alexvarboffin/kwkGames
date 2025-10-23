package com.esporte.olimp.vai.jojo.fon.gam.domain.usecase

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.HealthRecord
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HealthRepository

class AddHealthRecordUseCase(private val healthRepository: HealthRepository) {
    suspend operator fun invoke(record: HealthRecord): Long {
        return healthRepository.addHealthRecord(record)
    }
}
