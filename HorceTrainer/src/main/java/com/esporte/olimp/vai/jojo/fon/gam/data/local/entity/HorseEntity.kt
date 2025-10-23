package com.esporte.olimp.vai.jojo.fon.gam.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "horses")
data class HorseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val breed: String?,
    val dateOfBirth: Date?,
    val gender: String?,
    val photoPath: String?,
    val weight: Int?,
    val weightGoal: Int?,
    val bodyTemperature: Double?,
    val pulse: Int?,
    val respiration: Int?,
    val height: Double?,
    val acthLevel: Double?
)
