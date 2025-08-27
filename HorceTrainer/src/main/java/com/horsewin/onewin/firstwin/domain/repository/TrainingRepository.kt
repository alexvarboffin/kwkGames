package com.horsewin.onewin.firstwin.domain.repository

import com.horsewin.onewin.firstwin.domain.model.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    suspend fun addTraining(training: Training): Long
    suspend fun updateTraining(training: Training)
    fun getTrainingsForHorse(horseId: Long): Flow<List<Training>>
    fun getTrainingById(trainingId: Long): Flow<Training?>
    suspend fun deleteTrainingById(trainingId: Long)
}
