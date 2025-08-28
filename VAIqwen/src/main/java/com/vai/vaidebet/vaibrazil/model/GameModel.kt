package com.vai.vaidebet.vaibrazil.model

data class GameState(
    val board: GameBoard,
    val moves: Int,
    val isGameWon: Boolean = false,
    val showGrid: Boolean = false
)

data class GameBoard(
    val width: Int,
    val height: Int,
    val blocks: List<Block>,
    val exit: Exit
)

data class Block(
    val id: Int,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val isTarget: Boolean = false
)

data class Exit(
    val x: Int,
    val y: Int
)
