package com.esporte.olimp.yay.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esporte.olimp.yay.data.local.dao.HealthRecordDao
import com.esporte.olimp.yay.data.local.dao.HorseDao
import com.esporte.olimp.yay.data.local.dao.TrainingDao
import com.esporte.olimp.yay.data.local.entity.HealthRecordEntity
import com.esporte.olimp.yay.data.local.entity.HorseEntity
import com.esporte.olimp.yay.data.local.entity.TrainingEntity

@Database(entities = [HorseEntity::class, TrainingEntity::class, HealthRecordEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HorseDatabase : RoomDatabase() {
    abstract fun horseDao(): HorseDao
    abstract fun trainingDao(): TrainingDao
    abstract fun healthRecordDao(): HealthRecordDao

    companion object {
        @Volatile
        private var INSTANCE: HorseDatabase? = null

        fun getDatabase(context: Context): HorseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HorseDatabase::class.java,
                    "horse_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
