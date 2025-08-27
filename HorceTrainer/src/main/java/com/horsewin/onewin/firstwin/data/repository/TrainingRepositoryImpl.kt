package com.horsewin.onewin.firstwin.data.repository

import com.horsewin.onewin.firstwin.data.local.dao.TrainingDao
import com.horsewin.onewin.firstwin.data.local.entity.TrainingEntity
import com.horsewin.onewin.firstwin.domain.model.Training
import com.horsewin.onewin.firstwin.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingRepositoryImpl(private val trainingDao: TrainingDao) : TrainingRepository {
    override suspend fun addTraining(training: Training): Long {
        return trainingDao.insertTraining(training.toEntity())
    }

    override suspend fun updateTraining(training: Training) {
        trainingDao.updateTraining(training.toEntity())
    }

    override fun getTrainingsForHorse(horseId: Long): Flow<List<Training>> {
        return trainingDao.getTrainingsForHorse(horseId).map { trainings ->
            trainings.map { it.toDomain() }
        }
    }

    override fun getTrainingById(trainingId: Long): Flow<Training?> {
        return trainingDao.getTrainingById(trainingId).map { it?.toDomain() }
    }

    override suspend fun deleteTrainingById(trainingId: Long) {
        trainingDao.deleteTrainingById(trainingId)
    }
}

fun Training.toEntity(): TrainingEntity {
    return TrainingEntity(
        id = this.id,
        horseId = this.horseId,
        name = this.name,
        date = this.date,
        durationMinutes = this.durationMinutes,
        surfaceType = this.surfaceType,
        distanceMeters = this.distanceMeters,
        averageSpeedKmh = this.averageSpeedKmh,
        transitionsWalk = this.transitionsWalk,
        transitionsTrot = this.transitionsTrot,
        notes = this.notes
    )
}

fun TrainingEntity.toDomain(): Training {
    return Training(
        id = this.id,
        horseId = this.horseId,
        name = this.name,
        date = this.date,
        durationMinutes = this.durationMinutes,
        surfaceType = this.surfaceType,
        distanceMeters = this.distanceMeters,
        averageSpeedKmh = this.averageSpeedKmh,
        transitionsWalk = this.transitionsWalk,
        transitionsTrot = this.transitionsTrot,
        notes = this.notes
    )
}
