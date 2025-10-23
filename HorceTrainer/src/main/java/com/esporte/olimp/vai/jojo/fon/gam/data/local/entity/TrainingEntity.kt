package com.esporte.olimp.vai.jojo.fon.gam.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val horseId: Long,
    val name: String,
    val date: Date,
    val durationMinutes: Int,
    val surfaceType: String,
    val distanceMeters: Int,
    val averageSpeedKmh: Double,
    val transitionsWalk: Int?,
    val transitionsTrot: Int?,
    val notes: String?
)
