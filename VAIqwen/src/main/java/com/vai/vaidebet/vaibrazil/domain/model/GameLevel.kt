package com.vai.vaidebet.vaibrazil.domain.model

data class GameLevel(
    val id: Int,
    val name: String,
    val blocks: List<Block>,
    val minMoves: Int
)