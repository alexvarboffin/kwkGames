package com.vai.vaidebet.vaibrazil.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vai.vaidebet.vaibrazil.model.GameBoard

@Composable
fun LevelCard(level: Int, board: GameBoard) {
    Card(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Level $level",
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                board.blocks.forEach { block ->
                    Box(
                        modifier = Modifier
                            .offset((block.x * 10).dp, (block.y * 10).dp)
                            .size((block.width * 10).dp, (block.height * 10).dp)
                            .background(if (block.isTarget) Color.Red else Color.Green)
                    )
                }
            }
        }
    }
}
