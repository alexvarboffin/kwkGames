package com.esporte.olimp.yay.domain.repository

import com.esporte.olimp.yay.domain.model.HealthRecord
import kotlinx.coroutines.flow.Flow

interface HealthRepository {
    suspend fun addHealthRecord(record: HealthRecord): Long
    suspend fun updateHealthRecord(record: HealthRecord)
    fun getHealthRecordsForHorse(horseId: Long): Flow<List<HealthRecord>>
    fun getHealthRecordById(recordId: Long): Flow<HealthRecord?>
    suspend fun deleteHealthRecordById(recordId: Long)
}
