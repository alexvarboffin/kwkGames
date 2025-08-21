package com.mostbet.cricmost

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class Player(
    val id: Int,
    var name: String,
    var level: Int,
    var stats: MutableState<PlayerStats>,
    var position: MutableState<Pair<Float, Float>>
)

data class PlayerStats(
    var strength: Int,
    var dexterity: Int,
    var stamina: Int
)
object Grid {
    const val PADDING_HORIZONTAL = 0.05f
    const val PADDING_VERTICAL = 0.05f
    const val GRID_WIDTH = 9
    const val GRID_HEIGHT = 20

    val points: List<Pair<Float, Float>> = (0 until GRID_WIDTH).flatMap { x ->
        (0 until GRID_HEIGHT).map { y ->
            val gridX = (1 - PADDING_HORIZONTAL * 2) / (GRID_WIDTH - 1) * x + PADDING_HORIZONTAL
            val gridY = (1 - PADDING_VERTICAL * 2) / (GRID_HEIGHT - 1) * y + PADDING_VERTICAL
            gridX to gridY
        }
    }
}

object Formations {
    // Idealized positions, will be snapped to the nearest grid point
    val formations = mapOf(
        "4-4-2" to listOf(
            0.5f to 0.1f, // GK
            0.2f to 0.25f, 0.4f to 0.25f, 0.6f to 0.25f, 0.8f to 0.25f, // Def
            0.2f to 0.5f, 0.4f to 0.5f, 0.6f to 0.5f, 0.8f to 0.5f, // Mid
            0.4f to 0.75f, 0.6f to 0.75f // Fwd
        ),
        "4-3-3" to listOf(
            0.5f to 0.1f, // GK
            0.2f to 0.25f, 0.4f to 0.25f, 0.6f to 0.25f, 0.8f to 0.25f, // Def
            0.3f to 0.5f, 0.5f to 0.5f, 0.7f to 0.5f, // Mid
            0.3f to 0.75f, 0.5f to 0.75f, 0.7f to 0.75f // Fwd
        ),
        "5-3-2" to listOf(
            0.5f to 0.1f, // GK
            0.15f to 0.25f, 0.3f to 0.25f, 0.5f to 0.25f, 0.7f to 0.25f, 0.85f to 0.25f, // Def
            0.3f to 0.5f, 0.5f to 0.5f, 0.7f to 0.5f, // Mid
            0.4f to 0.75f, 0.6f to 0.75f // Fwd
        )
    )
}

fun findNearestGridPoint(point: Pair<Float, Float>, grid: List<Pair<Float, Float>>, occupied: Set<Pair<Float, Float>>): Pair<Float, Float> {
    return grid.filterNot { occupied.contains(it) }
        .minByOrNull { (gridX, gridY) ->
            sqrt((gridX - point.first).pow(2) + (gridY - point.second).pow(2))
        } ?: point // Fallback to original point if grid is full
}

fun getInitialPlayers(formationName: String = "4-4-2"): List<Player> {
    val idealPositions = Formations.formations[formationName]!!
    val occupied = mutableSetOf<Pair<Float, Float>>()
    val snappedPositions = idealPositions.map { idealPos ->
        val snapped = findNearestGridPoint(idealPos, Grid.points, occupied)
        occupied.add(snapped)
        snapped
    }

    return (1..11).map { i ->
        Player(
            id = i,
            name = "Player $i",
            level = 1,
            stats = mutableStateOf(PlayerStats(10, 10, 10)),
            position = mutableStateOf(snappedPositions[i - 1])
        )
    }
}

const val PREFS_NAME = "TacticFieldPrefs"
const val PLAYERS_DATA_KEY = "playersData"

fun savePlayersData(context: Context, players: List<Player>) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
    val dataString = players.joinToString(";") { p ->
        "${p.id},${p.name},${p.level},${p.stats.value.strength},${p.stats.value.dexterity},${p.stats.value.stamina},${p.position.value.first},${p.position.value.second}"
    }
    prefs.putString(PLAYERS_DATA_KEY, dataString)
    prefs.apply()
}

fun loadPlayersData(context: Context): List<Player> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val dataString = prefs.getString(PLAYERS_DATA_KEY, null)
    if (dataString.isNullOrEmpty()) {
        return getInitialPlayers()
    }
    return try {
        dataString.split(";").map { playerDataString ->
            val parts = playerDataString.split(",")
            Player(
                id = parts[0].toInt(),
                name = parts[1],
                level = parts[2].toInt(),
                stats = mutableStateOf(
                    PlayerStats(
                        strength = parts[3].toInt(),
                        dexterity = parts[4].toInt(),
                        stamina = parts[5].toInt()
                    )
                ),
                position = mutableStateOf(parts[6].toFloat() to parts[7].toFloat())
            )
        }
    } catch (e: Exception) {
        getInitialPlayers()
    }
}

@Composable
fun GameScreen(isEndlessMode: Boolean, level: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    val players = remember { mutableStateListOf(*loadPlayersData(context).toTypedArray()) }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    var formationDropdownExpanded by remember { mutableStateOf(false) }
    var fieldSize by remember { mutableStateOf(IntSize.Zero) }

    fun changeFormation(formationName: String) {
        val idealPositions = Formations.formations[formationName]!!
        val occupied = mutableSetOf<Pair<Float, Float>>()
        val snappedPositions = idealPositions.map { idealPos ->
            val snapped = findNearestGridPoint(idealPos, Grid.points, occupied)
            occupied.add(snapped)
            snapped
        }
        players.forEachIndexed { index, player ->
            player.position.value = snappedPositions.getOrElse(index) { 0.5f to 0.5f }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A222C))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val buttonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63))
            Button(onClick = { savePlayersData(context, players) }, colors = buttonColors) {
                Text("Save")
            }
            Button(onClick = {
                val loadedPlayers = loadPlayersData(context)
                players.clear()
                players.addAll(loadedPlayers)
            }, colors = buttonColors) {
                Text("Load")
            }
            Button(onClick = {
                players.clear()
                players.addAll(getInitialPlayers())
            }, colors = buttonColors) {
                Text("Reset")
            }
            Box {
                Button(onClick = { formationDropdownExpanded = true }, colors = buttonColors) {
                    Text("Formation")
                }
                DropdownMenu(
                    expanded = formationDropdownExpanded,
                    onDismissRequest = { formationDropdownExpanded = false }
                ) {
                    Formations.formations.keys.forEach { formationName ->
                        DropdownMenuItem(text = { Text(formationName) }, onClick = {
                            changeFormation(formationName)
                            formationDropdownExpanded = false
                        })
                    }
                }
            }
        }

        // Field
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { fieldSize = it }
        ) {
            val fieldWidth = fieldSize.width.toFloat()
            val fieldHeight = fieldSize.height.toFloat()

            // Striped background
            Row(modifier = Modifier.fillMaxSize()) {
                val stripeColor1 = Color(0xFF3A8A53)
                val stripeColor2 = Color(0xFF36814E)
                repeat(10) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(if (it % 2 == 0) stripeColor1 else stripeColor2))
                }
            }

            // Field Markings
            FieldMarkings()

            if (fieldSize != IntSize.Zero) {
                FormationMarkers(Grid.points, fieldWidth, fieldHeight)

                players.forEach { player ->
                    PlayerDraggable(
                        player = player,
                        allPlayers = players,
                        fieldWidth = fieldWidth,
                        fieldHeight = fieldHeight,
                        onPlayerClick = { selectedPlayer = it }
                    )
                }
            }

            if (selectedPlayer != null) {
                PlayerDetailsDialog(
                    player = selectedPlayer!!,
                    onDismiss = { selectedPlayer = null },
                    onStatIncrease = { stat ->
                        val currentStats = selectedPlayer!!.stats.value
                        when (stat) {
                            "Strength" -> selectedPlayer!!.stats.value = currentStats.copy(strength = currentStats.strength + 1)
                            "Dexterity" -> selectedPlayer!!.stats.value = currentStats.copy(dexterity = currentStats.dexterity + 1)
                            "Stamina" -> selectedPlayer!!.stats.value = currentStats.copy(stamina = currentStats.stamina + 1)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FormationMarkers(positions: List<Pair<Float, Float>>, fieldWidth: Float, fieldHeight: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        positions.forEach { position ->
            val x = position.first * fieldWidth
            val y = position.second * fieldHeight
            val markerSize = 10.dp.toPx()

            drawLine(
                color = Color.White.copy(alpha = 0.3f),
                start = Offset(x - markerSize, y - markerSize),
                end = Offset(x + markerSize, y + markerSize),
                strokeWidth = 2.dp.toPx()
            )
            drawLine(
                color = Color.White.copy(alpha = 0.3f),
                start = Offset(x - markerSize, y + markerSize),
                end = Offset(x + markerSize, y - markerSize),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

@Composable
fun FieldMarkings() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Center Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.White.copy(alpha = 0.5f))
                .align(Alignment.Center)
        )
        // Center Circle
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)), CircleShape)
                .align(Alignment.Center)
        )
        // Penalty Box Top
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
                .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)))
                .align(Alignment.TopCenter)
        )
        // Penalty Box Bottom
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(100.dp)
                .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)))
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PlayerDraggable(
    player: Player,
    allPlayers: List<Player>,
    fieldWidth: Float,
    fieldHeight: Float,
    onPlayerClick: (Player) -> Unit
) {
    val playerSizeDp = 60.dp
    val playerRadiusPx = with(LocalDensity.current) { playerSizeDp.toPx() / 2 }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    (player.position.value.first * fieldWidth - playerRadiusPx).roundToInt(),
                    (player.position.value.second * fieldHeight - playerRadiusPx).roundToInt()
                )
            }
            .size(playerSizeDp)
            .clip(CircleShape)
            .background(Brush.radialGradient(listOf(Color(0xFFD75A23), Color(0xFFB04A1A))))
            .border(BorderStroke(2.dp, Color(0xFFF08A5A)), CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        val currentPositions = allPlayers.map { it.position.value }.toSet()
                        val nearestPoint = findNearestGridPoint(player.position.value, Grid.points, currentPositions - player.position.value)
                        player.position.value = nearestPoint
                    }
                ) { change, dragAmount ->
                    change.consume()
                    val newPosX =
                        (player.position.value.first * fieldWidth + dragAmount.x) / fieldWidth
                    val newPosY =
                        (player.position.value.second * fieldHeight + dragAmount.y) / fieldHeight
                    player.position.value = newPosX to newPosY
                }
            }
            .clickable { onPlayerClick(player) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Player Icon",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(text = player.name, color = Color.White, fontSize = 10.sp)
        }
    }
}

@Composable
fun PlayerDetailsDialog(player: Player, onDismiss: () -> Unit, onStatIncrease: (String) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A222C))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = player.name, color = Color.White, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
                Text(text = "Level: ${player.level}", color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Stats", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                StatRow("Strength", player.stats.value.strength) { onStatIncrease("Strength") }
                StatRow("Dexterity", player.stats.value.dexterity) { onStatIncrease("Dexterity") }
                StatRow("Stamina", player.stats.value.stamina) { onStatIncrease("Stamina") }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63))
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun StatRow(name: String, value: Int, onIncrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$name: $value", color = Color.White)
        Button(
            onClick = onIncrease,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C4D63)),
            shape = CircleShape,
            modifier = Modifier.size(32.dp)
        ) {
            Text(text = "+")
        }
    }
}
