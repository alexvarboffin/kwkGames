package com.esporte.olimp.vai.jojo.fon.gam.domain.model

import java.util.Date

data class HealthRecord(
    val id: Long = 0,
    val horseId: Long,
    val date: Date,
    val weight: Int?,
    val bodyTemperature: Double?,
    val pulse: Int?,
    val respiration: Int?,
    val acthLevel: Double?,
    val comment: String?
)
