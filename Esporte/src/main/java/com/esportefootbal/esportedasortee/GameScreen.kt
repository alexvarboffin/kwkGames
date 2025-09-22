package com.esportedasorte.esportefootbal

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

const val ORIGINAL_WIDTH = 1360f
const val ORIGINAL_HEIGHT = 640f


@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val context = LocalContext.current

    val bgGame = ImageBitmap.imageResource(id = R.drawable.bg_game)
    val goal = ImageBitmap.imageResource(id = R.drawable.goal)
    val ballSpriteSheet = ImageBitmap.imageResource(id = R.drawable.ball)
    val ballShadow = ImageBitmap.imageResource(id = R.drawable.ball_shadow)

    val ballFrameWidth = ballSpriteSheet.width / 7
    val ballFrameHeight = ballSpriteSheet.height

    val goalkeeperSprites = remember(viewModel.goalkeeperAnimState) {
        (0 until getNumFrames(viewModel.goalkeeperAnimState)).mapNotNull {
            val resourceId = context.resources.getIdentifier("${viewModel.goalkeeperAnimState}_$it", "drawable", context.packageName)
            if (resourceId != 0) ImageBitmap.imageResource(context.resources, resourceId) else null
        }
    }

    val playerSprites = remember {
        (0 until 31).mapNotNull {
            val resourceId = context.resources.getIdentifier("player_$it", "drawable", context.packageName)
            if (resourceId != 0) ImageBitmap.imageResource(context.resources, resourceId) else null
        }
    }

    val ballPositionAnim = remember { Animatable(viewModel.ballTargetPosition, Offset.VectorConverter) }
    val ballScaleAnim = remember { Animatable(viewModel.ballTargetScale) }
    var goalkeeperFrame by remember { mutableStateOf(0) }
    var playerFrame by remember { mutableStateOf(0) }

    LaunchedEffect(viewModel.ballTargetPosition) {
        if (!viewModel.isPaused) {
            launch {
                ballPositionAnim.animateTo(viewModel.ballTargetPosition, animationSpec = tween(600))
            }
        }
    }
    LaunchedEffect(viewModel.ballTargetScale) {
        if (!viewModel.isPaused) {
            launch {
                ballScaleAnim.animateTo(viewModel.ballTargetScale, animationSpec = tween(600))
            }
        }
    }

    LaunchedEffect(viewModel.goalkeeperAnimState, viewModel.isPaused) {
        if (!viewModel.isPaused) {
            while (true) {
                delay(40)
                goalkeeperFrame = (goalkeeperFrame + 1) % getNumFrames(viewModel.goalkeeperAnimState)
            }
        }
    }

    LaunchedEffect(viewModel.playerKicking, viewModel.isPaused) {
        if (viewModel.playerKicking && !viewModel.isPaused) {
            for (i in 0 until 31) {
                playerFrame = i
                delay(30)
            }
            viewModel.playerKicking = false
        }
    }

    var dragAccumulator by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = Modifier.fillMaxSize(),      contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.bg_game),
            contentDescription = "Football field background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Canvas(

            modifier = Modifier
            .fillMaxWidth().aspectRatio(1.3f)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { 
                        dragAccumulator = Offset.Zero 
                    },
                    onDragEnd = { 
                        val scale = size.height / ORIGINAL_HEIGHT
                        viewModel.kick(dragAccumulator * -1f / scale, context)
                    }
                ) { change, dragAmount ->
                    change.consume()
                    dragAccumulator += dragAmount
                }
            }) {
            val scale = size.height / ORIGINAL_HEIGHT
            val offsetX = (size.width - ORIGINAL_WIDTH * scale) / 2f
            
            withTransform({
                translate(left = offsetX, top = 0f)
                scale(scale, scale, pivot = Offset.Zero)
            }) {
                drawImage(bgGame)
                drawImage(goal, topLeft = Offset(291f, 28f))

                if (goalkeeperSprites.isNotEmpty() && goalkeeperFrame < goalkeeperSprites.size) {
                    drawImage(goalkeeperSprites[goalkeeperFrame], topLeft = Offset(540f, 155f))
                }

                withTransform({
                    scale(ballScaleAnim.value, ballScaleAnim.value, pivot = ballPositionAnim.value)
                }) {
                     drawImage(ballShadow, topLeft = Offset(ballPositionAnim.value.x - 50, 580f), alpha = ballScaleAnim.value)
                     drawImage(
                        image = ballSpriteSheet,
                        srcOffset = IntOffset(viewModel.ballFrame * ballFrameWidth, 0),
                        srcSize = IntSize(ballFrameWidth, ballFrameHeight),
                        dstOffset = IntOffset(ballPositionAnim.value.x.toInt() - ballFrameWidth / 2, ballPositionAnim.value.y.toInt() - ballFrameHeight / 2),
                        dstSize = IntSize(ballFrameWidth, ballFrameHeight)
                     )
                }

                if (playerSprites.isNotEmpty() && playerFrame < playerSprites.size) {
                    drawImage(playerSprites[playerFrame], topLeft = Offset(530f, 100f))
                }
            }
        }

        GameUI(viewModel = viewModel, onPauseClick = { viewModel.togglePause() })
    }
}

fun getNumFrames(animName: String): Int {
    return when (animName) {
        "gk_idle" -> 24
        "gk_save_right" -> 34
        "gk_save_left" -> 34
        "gk_save_center_down" -> 51
        "gk_save_center_up" -> 25
        "gk_save_down_left" -> 34
        "gk_save_down_right" -> 34
        "gk_save_center" -> 25
        "gk_save_side_left" -> 30
        "gk_save_side_right" -> 30
        "gk_save_side_up_left" -> 30
        "gk_save_side_up_right" -> 30
        "gk_save_side_low_left" -> 51
        "gk_save_side_low_right" -> 51
        "gk_save_up_left" -> 36
        "gk_save_up_right" -> 36
        else -> 1
    }
}
