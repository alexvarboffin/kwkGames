package com.vai.vaidebet.vaibrazil.data.datasource

import android.content.Context
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.data.model.UnblockMeLevel
import com.vai.vaidebet.vaibrazil.data.model.UnblockMeBlock
import kotlinx.serialization.json.Json
import java.util.regex.Pattern

class LocalDataSource(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    fun getLevels(): List<GameLevel> {
        val levelsJsContent = context.assets.open("levels.js").bufferedReader().use { it.readText() }
        val pattern = Pattern.compile("\\[[\\s\\S]*\\]")
        val matcher = pattern.matcher(levelsJsContent)
        val jsonString: String

        if (matcher.find()) {
            jsonString = matcher.group()
        } else {
            throw IllegalStateException("Could not find JSON array in levels.js")
        }

        val unblockMeLevels = json.decodeFromString<List<UnblockMeLevel>>(jsonString)

        val hardcodedFirstLevel = GameLevel(
            id = 1,
            solutionMoves = 0, // Placeholder
            blocks = listOf(
                Block(id = 0, isTarget = true, orientation = Orientation.HORIZONTAL, length = 2, widthInGrid = 2, heightInGrid = 1, row = 2, col = 0),
                Block(id = 1, isTarget = false, orientation = Orientation.VERTICAL, length = 2, widthInGrid = 1, heightInGrid = 2, row = 0, col = 2),
                Block(id = 2, isTarget = false, orientation = Orientation.HORIZONTAL, length = 2, widthInGrid = 2, heightInGrid = 1, row = 2, col = 2)
            ),
            gridWidth = 6,
            gridHeight = 6,
            exitX = 6, // Right edge of 6x6 grid
            exitY = 2
        )

        val parsedLevels = unblockMeLevels.mapIndexed {
            index,
            unblockMeLevel ->
            val blocks = unblockMeLevel.b.mapIndexed {
                blockIndex,
                unblockMeBlock ->
                val orientation = if (unblockMeBlock.w > unblockMeBlock.h) {
                    Orientation.HORIZONTAL
                } else {
                    Orientation.VERTICAL
                }
                val length = if (orientation == Orientation.HORIZONTAL) unblockMeBlock.w else unblockMeBlock.h
                val x = unblockMeBlock.x ?: 0
                val y = unblockMeBlock.y ?: 0

                Block(
                    id = blockIndex,
                    isTarget = blockIndex == 0, // Assuming the first block is the target
                    orientation = orientation,
                    length = length,
                    widthInGrid = unblockMeBlock.w,
                    heightInGrid = unblockMeBlock.h,
                    row = y,
                    col = x
                )
            }
            GameLevel(
                id = index + 1,
                solutionMoves = 0, // Solution moves are not in the data, set to 0 for now
                blocks = blocks,
                gridWidth = unblockMeLevel.w,
                gridHeight = unblockMeLevel.h,
                exitX = unblockMeLevel.e.x,
                exitY = unblockMeLevel.e.y
            )
        }
        // Replace the first parsed level with the hardcoded one
        return parsedLevels.toMutableList().apply { this[0] = hardcodedFirstLevel }
    }
}