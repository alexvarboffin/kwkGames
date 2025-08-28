package com.vai.vaidebet.vaibrazil.domain.model

data class Block(
    val id: Int,
    val isTarget: Boolean,
    val orientation: Orientation,
    val length: Int,
    val row: Int,
    val col: Int,
    val shape: BlockShape = BlockShape.RECTANGLE
)

enum class Orientation {
    HORIZONTAL,
    VERTICAL,
    SQUARE
}

enum class BlockShape {
    RECTANGLE,
    L_SHAPED,
    T_SHAPED,
    Z_SHAPED
}