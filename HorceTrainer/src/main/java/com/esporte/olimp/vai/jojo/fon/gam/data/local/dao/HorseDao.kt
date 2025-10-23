package com.esporte.olimp.vai.jojo.fon.gam.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esporte.olimp.vai.jojo.fon.gam.data.local.entity.HorseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HorseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHorse(horse: HorseEntity): Long

    @Update
    suspend fun updateHorse(horse: HorseEntity)

    @Query("SELECT * FROM horses WHERE id = :horseId")
    fun getHorseById(horseId: Long): Flow<HorseEntity>

    @Query("SELECT * FROM horses")
    fun getAllHorses(): Flow<List<HorseEntity>>

    @Query("DELETE FROM horses WHERE id = :horseId")
    suspend fun deleteHorseById(horseId: Long)
}
