package com.vai.vaidebet.vaibrazil.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UnblockMeLevel(
    val b: List<UnblockMeBlock>,
    val e: UnblockMeExit,
    val w: Int,
    val h: Int,
    val c: String? = null
)

@Serializable
data class UnblockMeBlock(
    val x: Int? = null, // x is optional for horizontal blocks
    val y: Int? = null, // y is optional for vertical blocks
    val w: Int,
    val h: Int
)

@Serializable
data class UnblockMeExit(
    val x: Int,
    val y: Int
)
