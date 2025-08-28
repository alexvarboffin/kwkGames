package com.vai.vaidebet.vaibrazil.domain.model

data class GameLevel(
    val id: Int,
    val solutionMoves: Int,
    val blocks: List<Block>
) {
    fun isWon(): Boolean {
        val targetBlock = blocks.find { it.isTarget } ?: return false
        // Win condition: target block reaches the right edge
        return targetBlock.col + targetBlock.length == 6
    }
    
    fun calculateStars(moves: Int): Int {
        return when {
            moves <= solutionMoves -> 3
            moves <= solutionMoves * 1.5 -> 2
            else -> 1
        }
    }
}