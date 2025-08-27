package com.horsewin.onewin.firstwin.domain.model

import java.util.Date

data class Training(
    val id: Long = 0,
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
