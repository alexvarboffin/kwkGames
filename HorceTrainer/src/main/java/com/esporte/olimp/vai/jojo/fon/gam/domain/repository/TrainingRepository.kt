package com.esporte.olimp.vai.jojo.fon.gam.domain.repository

import com.esporte.olimp.vai.jojo.fon.gam.domain.model.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    suspend fun addTraining(training: Training): Long
    suspend fun updateTraining(training: Training)
    fun getTrainingsForHorse(horseId: Long): Flow<List<Training>>
    fun getTrainingById(trainingId: Long): Flow<Training?>
    suspend fun deleteTrainingById(trainingId: Long)
}
