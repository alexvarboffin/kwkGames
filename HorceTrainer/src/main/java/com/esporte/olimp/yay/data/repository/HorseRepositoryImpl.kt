package com.esporte.olimp.yay.data.repository

import com.esporte.olimp.yay.data.local.dao.HorseDao
import com.esporte.olimp.yay.data.local.entity.HorseEntity
import com.esporte.olimp.yay.domain.model.Horse
import com.esporte.olimp.yay.domain.repository.HorseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HorseRepositoryImpl(private val horseDao: HorseDao) : HorseRepository {
    override suspend fun addHorse(horse: Horse): Long {
        return horseDao.insertHorse(horse.toEntity())
    }

    override suspend fun updateHorse(horse: Horse) {
        horseDao.updateHorse(horse.toEntity())
    }

    override fun getHorseById(horseId: Long): Flow<Horse?> {
        return horseDao.getHorseById(horseId).map { it?.toDomain() }
    }

    override fun getAllHorses(): Flow<List<Horse>> {
        return horseDao.getAllHorses().map { horses ->
            horses.map { it.toDomain() }
        }
    }

    override suspend fun deleteHorseById(horseId: Long) {
        horseDao.deleteHorseById(horseId)
    }
}

fun Horse.toEntity(): HorseEntity {
    return HorseEntity(
        id = this.id,
        name = this.name,
        breed = this.breed,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        photoPath = this.photoPath,
        weight = this.weight,
        weightGoal = this.weightGoal,
        bodyTemperature = this.bodyTemperature,
        pulse = this.pulse,
        respiration = this.respiration,
        height = this.height,
        acthLevel = this.acthLevel
    )
}

fun HorseEntity.toDomain(): Horse {
    return Horse(
        id = this.id,
        name = this.name,
        breed = this.breed,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        photoPath = this.photoPath,
        weight = this.weight,
        weightGoal = this.weightGoal,
        bodyTemperature = this.bodyTemperature,
        pulse = this.pulse,
        respiration = this.respiration,
        height = this.height,
        acthLevel = this.acthLevel
    )
}
