package com.esporte.olimp.yay.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "health_records")
data class HealthRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val horseId: Long,
    val date: Date,
    val weight: Int?,
    val bodyTemperature: Double?,
    val pulse: Int?,
    val respiration: Int?,
    val acthLevel: Double?,
    val comment: String? // For general comments or metric type if not covered by specific fields
)
