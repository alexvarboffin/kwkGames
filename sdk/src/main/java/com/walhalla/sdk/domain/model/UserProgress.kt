package com.walhalla.sdk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProgress(
    val highestUnlockedLevel: Int,
    val stars: Map<String, Int>
)
