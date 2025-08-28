package com.vai.vaidebet.vaibrazil.data.repository

import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.domain.repository.GameRepository

class GameRepositoryImpl : GameRepository {
    override suspend fun getLevels(): List<GameLevel> {
        return listOf(
            GameLevel(
                id = 1,
                name = "Уровень 1",
                blocks = listOf(
                    Block(1, 2, 0, 2, Orientation.HORIZONTAL, true),
                    Block(2, 0, 0, 2, Orientation.VERTICAL),
                    Block(3, 0, 3, 2, Orientation.VERTICAL),
                    Block(4, 2, 3, 2, Orientation.HORIZONTAL),
                    Block(5, 3, 1, 2, Orientation.HORIZONTAL),
                    Block(6, 4, 0, 2, Orientation.VERTICAL),
                    Block(7, 4, 3, 2, Orientation.VERTICAL)
                ),
                minMoves = 10
            ),
            GameLevel(
                id = 2,
                name = "Уровень 2",
                blocks = listOf(
                    Block(1, 2, 1, 2, Orientation.HORIZONTAL, true),
                    Block(2, 0, 0, 2, Orientation.VERTICAL),
                    Block(3, 0, 3, 2, Orientation.VERTICAL),
                    Block(4, 1, 1, 2, Orientation.VERTICAL),
                    Block(5, 3, 0, 3, Orientation.HORIZONTAL),
                    Block(6, 4, 2, 2, Orientation.VERTICAL),
                    Block(7, 4, 4, 2, Orientation.VERTICAL)
                ),
                minMoves = 15
            ),
            GameLevel(
                id = 3,
                name = "Уровень 3",
                blocks = listOf(
                    Block(1, 2, 0, 2, Orientation.HORIZONTAL, true),
                    Block(2, 0, 1, 2, Orientation.VERTICAL),
                    Block(3, 0, 4, 2, Orientation.VERTICAL),
                    Block(4, 1, 2, 2, Orientation.HORIZONTAL),
                    Block(5, 3, 1, 2, Orientation.HORIZONTAL),
                    Block(6, 3, 4, 2, Orientation.VERTICAL),
                    Block(7, 4, 0, 2, Orientation.VERTICAL),
                    Block(8, 4, 3, 2, Orientation.VERTICAL)
                ),
                minMoves = 20
            )
        )
    }

    override suspend fun getLevelById(id: Int): GameLevel? {
        return getLevels().find { it.id == id }
    }
}