package com.cricmost.cricmst

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LevelEndDialog(score: Int, goal: Int, onDismiss: () -> Unit) {
    val isComplete = score >= goal
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                Modifier.Companion.padding(24.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Text(
                    if (isComplete) "LEVEL COMPLETE!" else "LEVEL FAILED",
                    fontWeight = FontWeight.Companion.Bold
                ); Text("SCORE: $score / $goal"); Button(onClick = onDismiss) { Text("Continue") }
            }
        }
    }
}