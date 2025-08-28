package com.vai.vaidebet.vaibrazil.domain.model

data class Block(
    val id: Int,
    val isTarget: Boolean,
    val orientation: Orientation,
    val length: Int,
    val row: Int,
    val col: Int
)

enum class Orientation {
    HORIZONTAL,
    VERTICAL,
    SQUARE
}