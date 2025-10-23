package com.esporte.olimp.vai.jojo.fon.gam.data.repository

import com.esporte.olimp.vai.jojo.fon.gam.data.local.dao.HealthRecordDao
import com.esporte.olimp.vai.jojo.fon.gam.data.local.entity.HealthRecordEntity
import com.esporte.olimp.vai.jojo.fon.gam.domain.model.HealthRecord
import com.esporte.olimp.vai.jojo.fon.gam.domain.repository.HealthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class HealthRepositoryImpl(private val healthRecordDao: HealthRecordDao) : HealthRepository {
    override suspend fun addHealthRecord(record: HealthRecord): Long {
        return healthRecordDao.insertHealthRecord(record.toEntity())
    }

    override suspend fun updateHealthRecord(record: HealthRecord) {
        healthRecordDao.updateHealthRecord(record.toEntity())
    }

    override fun getHealthRecordsForHorse(horseId: Long): Flow<List<HealthRecord>> {
        return healthRecordDao.getHealthRecordsForHorse(horseId).map { records ->
            records.map { it.toDomain() }
        }
    }

    override fun getHealthRecordById(recordId: Long): Flow<HealthRecord?> {
        return healthRecordDao.getHealthRecordById(recordId).map { it?.toDomain() }
    }

    override suspend fun deleteHealthRecordById(recordId: Long) {
        healthRecordDao.deleteHealthRecordById(recordId)
    }
}

fun HealthRecord.toEntity(): HealthRecordEntity {
    return HealthRecordEntity(
        id = this.id,
        horseId = this.horseId,
        date = this.date,
        weight = this.weight,
        bodyTemperature = this.bodyTemperature,
        pulse = this.pulse,
        respiration = this.respiration,
        acthLevel = this.acthLevel,
        comment = this.comment
    )
}

fun HealthRecordEntity.toDomain(): HealthRecord {
    return HealthRecord(
        id = this.id,
        horseId = this.horseId,
        date = this.date,
        weight = this.weight,
        bodyTemperature = this.bodyTemperature,
        pulse = this.pulse,
        respiration = this.respiration,
        acthLevel = this.acthLevel,
        comment = this.comment
    )
}
