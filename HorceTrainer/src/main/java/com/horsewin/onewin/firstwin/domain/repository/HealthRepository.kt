package com.horsewin.onewin.firstwin.domain.repository

import com.horsewin.onewin.firstwin.domain.model.HealthRecord
import kotlinx.coroutines.flow.Flow

interface HealthRepository {
    suspend fun addHealthRecord(record: HealthRecord): Long
    suspend fun updateHealthRecord(record: HealthRecord)
    fun getHealthRecordsForHorse(horseId: Long): Flow<List<HealthRecord>>
    fun getHealthRecordById(recordId: Long): Flow<HealthRecord?>
    suspend fun deleteHealthRecordById(recordId: Long)
}
