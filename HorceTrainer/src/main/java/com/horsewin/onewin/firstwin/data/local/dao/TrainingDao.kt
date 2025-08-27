package com.horsewin.onewin.firstwin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.horsewin.onewin.firstwin.data.local.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraining(training: TrainingEntity): Long

    @Update
    suspend fun updateTraining(training: TrainingEntity)

    @Query("SELECT * FROM trainings WHERE horseId = :horseId ORDER BY date DESC")
    fun getTrainingsForHorse(horseId: Long): Flow<List<TrainingEntity>>

    @Query("SELECT * FROM trainings WHERE id = :trainingId")
    fun getTrainingById(trainingId: Long): Flow<TrainingEntity>

    @Query("DELETE FROM trainings WHERE id = :trainingId")
    suspend fun deleteTrainingById(trainingId: Long)
}
