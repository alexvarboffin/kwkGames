package com.vai.vai.vaidebet.vaibrazil.data.datasource

import android.content.Context
import com.vai.vai.vaidebet.vaibrazil.data.model.UnblockMeLevel
import com.walhalla.sdk.domain.model.Block

import com.walhalla.sdk.domain.model.GameLevel
import com.walhalla.sdk.domain.model.Orientation
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
                    row = unblockMeLevel.h - y - unblockMeBlock.h,
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
                exitY = unblockMeLevel.h - unblockMeLevel.e.y - 1
            )
        }
        return parsedLevels
    }

    fun getTotalLevels(): Int {
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
        return unblockMeLevels.size
    }
}