package com.cricjojo.cricmost

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun LevelScreen(onLevelClick: (Int) -> Unit) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val unlockedLevel by remember { mutableIntStateOf(sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 3)) }

    Box(modifier = Modifier.Companion.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_level_select_background),
            contentDescription = "Level Select Background",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.Companion.fillMaxSize()
        )
        Column {
            Box(modifier = Modifier.Companion.height(30.dp))
            Column(
                modifier = Modifier.Companion.fillMaxSize(),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Text(
                    text = "",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color.Companion.White,
                    modifier = Modifier.Companion.padding(vertical = 32.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.Companion.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(30) { levelIndex ->
                        val currentLevel = levelIndex + 1
                        val isLocked = currentLevel > unlockedLevel
                        LevelItem(level = currentLevel, stars = 0, isLocked = isLocked) {
                            onLevelClick(currentLevel)
                        }
                    }
                }
            }
        }
    }
}