package com.walhalla.sdk.domain.model

data class Block(
    val id: Int,
    val isTarget: Boolean,
    val orientation: Orientation,
    val length: Int, // This is either width or height depending on orientation
    val widthInGrid: Int = -1, // New field
    val heightInGrid: Int= -1, // New field
    val row: Int,
    val col: Int
)

enum class Orientation {
    HORIZONTAL,
    VERTICAL
}