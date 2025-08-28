package com.vai.vaidebet.vaibrazil.data.datasource

import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation

class LocalDataSource {
    fun getLevels(): List<GameLevel> {
        return listOf(
            GameLevel(
                id = 1,
                solutionMoves = 10,
                blocks = listOf(
                    Block(id = 1, isTarget = true, orientation = Orientation.HORIZONTAL, length = 2, row = 2, col = 0),
                    Block(id = 2, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 0, col = 0),
                    Block(id = 3, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 3, col = 0),
                    Block(id = 4, isTarget = false, orientation = Orientation.HORIZONTAL, length = 2, row = 0, col = 1),
                    Block(id = 5, isTarget = false, orientation = Orientation.HORIZONTAL, length = 2, row = 4, col = 1),
                    Block(id = 6, isTarget = false, orientation = Orientation.VERTICAL, length = 3, row = 1, col = 2),
                    Block(id = 7, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 0, col = 3),
                    Block(id = 8, isTarget = false, orientation = Orientation.VERTICAL, length = 3, row = 2, col = 3),
                    Block(id = 9, isTarget = false, orientation = Orientation.HORIZONTAL, length = 2, row = 5, col = 4),
                    Block(id = 10, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 0, col = 5),
                )
            ),
            GameLevel(
                id = 2,
                solutionMoves = 15,
                blocks = listOf(
                    Block(id = 1, isTarget = true, orientation = Orientation.HORIZONTAL, length = 2, row = 2, col = 1),
                    Block(id = 2, isTarget = false, orientation = Orientation.SQUARE, length = 2, row = 0, col = 0),
                    Block(id = 3, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 3, col = 0),
                    Block(id = 4, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 0, col = 2),
                    Block(id = 5, isTarget = false, orientation = Orientation.HORIZONTAL, length = 3, row = 4, col = 1),
                    Block(id = 6, isTarget = false, orientation = Orientation.VERTICAL, length = 2, row = 0, col = 3),
                    Block(id = 7, isTarget = false, orientation = Orientation.HORIZONTAL, length = 2, row = 5, col = 3),
                )
            )
        )
    }
}