package com.vai.vaidebet.vaibrazil.data

import com.vai.vaidebet.vaibrazil.model.Block
import com.vai.vaidebet.vaibrazil.model.GameBoard
import com.vai.vaidebet.vaibrazil.model.Exit

class LevelRepository {

    fun getLevel(level: Int): GameBoard {
        return when (level) {
            1 -> level1()
            2 -> level2()
            3 -> level3()
            4 -> level4()
            5 -> level5()
            6 -> level6()
            7 -> level7()
            8 -> level8()
            9 -> level9()
            10 -> level10()
            else -> level1() // Default to level 1
        }
    }

    private fun level1(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 0, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 0, y = 0, width = 1, height = 2),
            Block(id = 3, x = 1, y = 0, width = 2, height = 1),
            Block(id = 4, x = 3, y = 0, width = 1, height = 3),
            Block(id = 5, x = 0, y = 3, width = 2, height = 1),
            Block(id = 6, x = 2, y = 3, width = 1, height = 2),
            Block(id = 7, x = 3, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level2(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 1, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 0, y = 0, width = 1, height = 2),
            Block(id = 3, x = 1, y = 0, width = 2, height = 1),
            Block(id = 4, x = 3, y = 0, width = 1, height = 3),
            Block(id = 5, x = 0, y = 3, width = 2, height = 1),
            Block(id = 6, x = 2, y = 3, width = 1, height = 2),
            Block(id = 7, x = 3, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level3(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 2, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 0, y = 0, width = 1, height = 2),
            Block(id = 3, x = 1, y = 0, width = 2, height = 1),
            Block(id = 4, x = 3, y = 0, width = 1, height = 3),
            Block(id = 5, x = 0, y = 3, width = 2, height = 1),
            Block(id = 6, x = 2, y = 3, width = 1, height = 2),
            Block(id = 7, x = 3, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level4(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 3, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 0, y = 0, width = 1, height = 2),
            Block(id = 3, x = 1, y = 0, width = 2, height = 1),
            Block(id = 4, x = 3, y = 0, width = 1, height = 3),
            Block(id = 5, x = 0, y = 3, width = 2, height = 1),
            Block(id = 6, x = 2, y = 3, width = 1, height = 2),
            Block(id = 7, x = 3, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level5(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 4, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 0, y = 0, width = 1, height = 2),
            Block(id = 3, x = 1, y = 0, width = 2, height = 1),
            Block(id = 4, x = 3, y = 0, width = 1, height = 3),
            Block(id = 5, x = 0, y = 3, width = 2, height = 1),
            Block(id = 6, x = 2, y = 3, width = 1, height = 2),
            Block(id = 7, x = 3, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level6(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 0, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 1, y = 0, width = 1, height = 2),
            Block(id = 3, x = 2, y = 0, width = 2, height = 1),
            Block(id = 4, x = 4, y = 0, width = 1, height = 3),
            Block(id = 5, x = 1, y = 3, width = 2, height = 1),
            Block(id = 6, x = 3, y = 3, width = 1, height = 2),
            Block(id = 7, x = 4, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level7(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 1, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 2, y = 0, width = 1, height = 2),
            Block(id = 3, x = 3, y = 0, width = 2, height = 1),
            Block(id = 4, x = 5, y = 0, width = 1, height = 3),
            Block(id = 5, x = 2, y = 3, width = 2, height = 1),
            Block(id = 6, x = 4, y = 3, width = 1, height = 2),
            Block(id = 7, x = 5, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level8(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 2, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 3, y = 0, width = 1, height = 2),
            Block(id = 3, x = 4, y = 0, width = 2, height = 1),
            Block(id = 4, x = 0, y = 0, width = 1, height = 3),
            Block(id = 5, x = 3, y = 3, width = 2, height = 1),
            Block(id = 6, x = 5, y = 3, width = 1, height = 2),
            Block(id = 7, x = 0, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level9(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 3, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 4, y = 0, width = 1, height = 2),
            Block(id = 3, x = 5, y = 0, width = 2, height = 1),
            Block(id = 4, x = 1, y = 0, width = 1, height = 3),
            Block(id = 5, x = 4, y = 3, width = 2, height = 1),
            Block(id = 6, x = 0, y = 3, width = 1, height = 2),
            Block(id = 7, x = 1, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }

    private fun level10(): GameBoard {
        val blocks = listOf(
            Block(id = 1, x = 4, y = 2, width = 2, height = 1, isTarget = true),
            Block(id = 2, x = 5, y = 0, width = 1, height = 2),
            Block(id = 3, x = 0, y = 0, width = 2, height = 1),
            Block(id = 4, x = 2, y = 0, width = 1, height = 3),
            Block(id = 5, x = 5, y = 3, width = 2, height = 1),
            Block(id = 6, x = 1, y = 3, width = 1, height = 2),
            Block(id = 7, x = 2, y = 4, width = 2, height = 1)
        )
        return GameBoard(width = 6, height = 5, blocks = blocks, exit = Exit(6, 2))
    }
}
