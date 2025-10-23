package com.esporte.olimp.vai.jojo.fon.gam.domain.model

import java.util.Date

data class Horse(
    val id: Long = 0,
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
