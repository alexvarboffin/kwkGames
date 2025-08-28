package com.vai.vaidebet.vaibrazil.domain.model

data class GameLevel(
    val id: Int,
    val blocks: List<Block>,
    val solutionMoves: Int
)
