package com.horsewin.onewin.firstwin.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.horsewin.onewin.firstwin.data.local.entity.HealthRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthRecord(record: HealthRecordEntity): Long

    @Update
    suspend fun updateHealthRecord(record: HealthRecordEntity)

    @Query("SELECT * FROM health_records WHERE horseId = :horseId ORDER BY date DESC")
    fun getHealthRecordsForHorse(horseId: Long): Flow<List<HealthRecordEntity>>

    @Query("SELECT * FROM health_records WHERE id = :recordId")
    fun getHealthRecordById(recordId: Long): Flow<HealthRecordEntity>

    @Query("DELETE FROM health_records WHERE id = :recordId")
    suspend fun deleteHealthRecordById(recordId: Long)
}
