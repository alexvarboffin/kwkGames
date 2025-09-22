package com.walhalla.sdk.domain.model

data class GameLevel(
    val id: Int,
    val solutionMoves: Int,
    val blocks: List<Block> = emptyList<Block>(),
    val gridWidth: Int= -1,
    val gridHeight: Int= -1,
    val exitX: Int= -1,
    val exitY: Int= -1
)