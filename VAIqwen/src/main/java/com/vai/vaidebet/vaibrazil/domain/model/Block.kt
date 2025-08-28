package com.vai.vaidebet.vaibrazil.domain.model

data class Block(
    val id: Int,
    val row: Int,
    val col: Int,
    val length: Int,
    val orientation: Orientation,
    val isTarget: Boolean = false
)