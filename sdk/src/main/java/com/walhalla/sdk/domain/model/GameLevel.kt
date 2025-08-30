package com.walhalla.sdk.domain.model

data class GameLevel(
    val id: Int,
    val solutionMoves: Int,
    val blocks: List<Block>,
    val gridWidth: Int,
    val gridHeight: Int,
    val exitX: Int,
    val exitY: Int
)