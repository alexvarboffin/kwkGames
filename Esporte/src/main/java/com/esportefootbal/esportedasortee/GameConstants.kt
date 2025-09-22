package com.esportefootbal.esportedasortee

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

// Game Dimensions
const val CANVAS_WIDTH = 1360
const val CANVAS_HEIGHT = 640
const val CANVAS_WIDTH_HALF = (CANVAS_WIDTH / 2).toFloat()
const val CANVAS_HEIGHT_HALF = (CANVAS_HEIGHT / 2).toFloat()
const val EDGEBOARD_X = 250
const val EDGEBOARD_Y = 20

// Game States
const val STATE_LOADING = 0
const val STATE_MENU = 1
const val STATE_HELP = 2 // Corrected from 1 in JS to avoid conflict
const val STATE_GAME = 3
const val STATE_INIT = 0
const val STATE_PLAY = 1
const val STATE_FINISH = 2
const val STATE_PAUSE = 3

// Input Events (Mapped to integers for consistency with JS)
const val ON_MOUSE_DOWN = 0
const val ON_MOUSE_UP = 1
const val ON_MOUSE_OVER = 2
const val ON_MOUSE_OUT = 3
const val ON_DRAG_START = 4
const val ON_DRAG_END = 5
const val ON_TWEEN_ENDED = 6
const val ON_BUT_NO_DOWN = 7
const val ON_BUT_YES_DOWN = 8

// Game Speed and Physics
const val FPS = 30
const val FPS_DESKTOP = 60
const val FPS_TIME = 1f / FPS
const val ROLL_BALL_RATE = 60f / FPS
const val STEP_RATE = 1.5f
const val PHYSICS_ACCURACY = 3
const val PHYSICS_STEP = 1f / (FPS * STEP_RATE)
const val BALL_VELOCITY_MULTIPLIER = 1f
const val BALL_MASS = 0.5f
const val BALL_RADIUS = 0.64f
const val BALL_LINEAR_DAMPING = 0.2f
const val MIN_BALL_VEL_ROTATION = 0.1f
const val HIT_BALL_MAX_FORCE = 130f
const val HIT_BALL_MIN_FORCE = 5f
const val FORCE_RATE = 0.0014f
const val FORCE_MAX = 0.5f
const val MAX_FORCE_Y = 66f
const val MIN_FORCE_Y = 50f
const val HAND_KEEPER_ANGLE_RATE = 0.15f
const val MOUSE_SENSIBILITY = 0.03f

// Timeouts and Delays (in milliseconds)
const val MS_TIME_SWIPE_END = 1000L
const val MS_TIME_SWIPE_START = 3000L
const val MS_TIME_FADE_HELP_TEXT = 500L
const val MS_WAIT_SHOW_GAME_OVER_PANEL = 250L
const val TIME_RESET_AFTER_GOAL = 1000L
const val TIME_POLE_COLLISION_RESET = 1000L
const val TIME_RESET_AFTER_BALL_OUT = 250L
const val TIME_RESET_AFTER_SAVE = 500L
const val MS_EFFECT_ADD = 1500L
const val MS_ROLLING_SCORE = 500L
const val BUFFER_ANIM_PLAYER = FPS // This is actually a time buffer, not FPS directly
const val TIME_SWIPE_MOBILE = 650L
const val TIME_SWIPE_DESKTOP = 500L

// UI and Text
const val FONT_GAME = "TradeGothic" // Will map to a font resource
val TEXT_SIZE = listOf(80, 100, 130)
const val LOCALSTORAGE_STRING_BEST_SCORE = "penalty_best_score" // Renamed for clarity
val TEXT_EXCELLENT_COLOR = listOf(Color(0xFFFFFFFF), Color(0xFF5D96FE))
val TEXT_COLOR = Color(0xFFFFFFFF)
val TEXT_COLOR_1 = Color(0xFFFF2222)
val TEXT_COLOR_STROKE = Color(0xFF002A59)
const val OUTLINE_WIDTH = 1.5f

// Game Specifics
const val LOCAL_BEST_SCORE = 0 // Index for LOCALSTORAGE_STRING
const val MAX_PERCENT_PROBABILITY = 100
const val SHOOT_FRAME = 7
const val GOAL_KEEPER_TOLLERANCE_LEFT = -4
const val GOAL_KEEPER_TOLLERANCE_RIGHT = 4
const val BALL_OUT_Y = 155f + 3f // BACK_WALL_GOAL_POSITION.y + 3
const val SHADOWN_FACTOR = 1.1f
const val BALL_SCALE_FACTOR = 0.07f
const val SOUNDTRACK_VOLUME_IN_GAME = 0.2f

// Goalkeeper Animations (Indices for SPRITE_NAME_GOALKEEPER and NUM_SPRITE_GOALKEEPER)
const val IDLE = 0
const val RIGHT = 1
const val LEFT = 2
const val CENTER_DOWN = 3
const val CENTER_UP = 4
const val LEFT_DOWN = 5
const val RIGHT_DOWN = 6
const val CENTER = 7
const val SIDE_LEFT = 8
const val SIDE_RIGHT = 9
const val SIDE_LEFT_UP = 10
const val SIDE_RIGHT_UP = 11
const val SIDE_LEFT_DOWN = 12
const val SIDE_RIGHT_DOWN = 13
const val LEFT_UP = 14
const val RIGHT_UP = 15

val SPRITE_NAME_GOALKEEPER = listOf(
    "gk_idle", "gk_save_right", "gk_save_left", "gk_save_center_down",
    "gk_save_center_up", "gk_save_down_left", "gk_save_down_right", "gk_save_center",
    "gk_save_side_left", "gk_save_side_right", "gk_save_side_up_left", "gk_save_side_up_right",
    "gk_save_side_low_left", "gk_save_side_low_right", "gk_save_up_left", "gk_save_up_right"
)

val NUM_SPRITE_GOALKEEPER = listOf(
    24, 34, 34, 51, 25, 34, 34, 25, 30, 30, 30, 30, 51, 51, 36, 36
)

val OFFSET_CONTAINER_GOALKEEPER = listOf(
    Offset(0f, 0f), Offset(15f, -29f), Offset(-360f, -29f), Offset(-15f, -15f),
    Offset(-20f, -85f), Offset(-355f, 20f), Offset(21f, 20f), Offset(10f, -10f),
    Offset(-140f, -30f), Offset(10f, -30f), Offset(-120f, -75f), Offset(14f, -75f),
    Offset(-140f, -10f), Offset(30f, -10f), Offset(-430f, -56f), Offset(-8f, -56f)
)

val ORIGIN_POINT_IMPACT_ANIMATION = listOf(
    Offset(0f, 0f), // Placeholder for null
    Offset(295.74f, 3.76f), Offset(-324.82f, 3.76f), Offset(4.8f, 0f), Offset(5f, 0f),
    Offset(-354f, 0f), Offset(334.5f, 0f), Offset(4.8f, 0f), Offset(-198.77f, 0f),
    Offset(189f, 0f), Offset(-208.4f, 0f), Offset(189f, 0f), Offset(-150f, 0f),
    Offset(101.8f, 0f), Offset(-344f, -88f), Offset(315f, -88f)
)

val ANIM_GOAL_KEEPER_FAIL_EXCLUSION_LIST = listOf(
    listOf(LEFT_UP, LEFT, SIDE_LEFT_UP, SIDE_LEFT),
    listOf(LEFT_UP, LEFT, SIDE_LEFT_UP, SIDE_LEFT, CENTER_UP),
    listOf(CENTER_UP, CENTER, SIDE_LEFT_UP, SIDE_RIGHT_UP, SIDE_LEFT, SIDE_RIGHT),
    listOf(RIGHT_UP, RIGHT, SIDE_RIGHT_UP, SIDE_RIGHT, CENTER_UP),
    listOf(RIGHT_UP, RIGHT, SIDE_RIGHT_UP, SIDE_RIGHT),
    listOf(LEFT_UP, LEFT, SIDE_LEFT_UP, SIDE_LEFT, SIDE_LEFT_DOWN, LEFT_DOWN),
    listOf(LEFT_UP, LEFT, SIDE_LEFT_UP, SIDE_LEFT, SIDE_LEFT_DOWN),
    listOf(CENTER_UP, CENTER, SIDE_LEFT_UP, SIDE_RIGHT_UP, CENTER_DOWN, SIDE_RIGHT, SIDE_LEFT),
    listOf(RIGHT_UP, RIGHT, SIDE_RIGHT_UP, SIDE_RIGHT, SIDE_RIGHT_DOWN),
    listOf(RIGHT_UP, RIGHT, SIDE_RIGHT_UP, SIDE_RIGHT, SIDE_RIGHT_DOWN, RIGHT_DOWN),
    listOf(LEFT_DOWN, LEFT, SIDE_LEFT_DOWN),
    listOf(LEFT_DOWN, LEFT, SIDE_LEFT_DOWN, SIDE_LEFT, SIDE_LEFT_UP),
    listOf(CENTER_DOWN, CENTER, CENTER_UP, SIDE_RIGHT, SIDE_LEFT, SIDE_RIGHT_DOWN, SIDE_LEFT_DOWN),
    listOf(RIGHT_DOWN, RIGHT, SIDE_RIGHT_DOWN, SIDE_RIGHT, SIDE_RIGHT_UP),
    listOf(RIGHT_DOWN, RIGHT, SIDE_RIGHT_DOWN)
)

// 3D related constants (will be used for coordinate mapping, not direct 3D rendering initially)
const val FOV = 15f
const val NEAR = 1f
const val FAR = 2000f
const val CANVAS_3D_OPACITY = 0.5f

// Data classes for complex constants
data class Point3D(val x: Float, val y: Float, val z: Float)
data class Size3D(val width: Float, val depth: Float, val height: Float)
data class SphereSize(val radius_top: Float, val radius_bottom: Float, val height: Float, val segments: Int)
data class ForceMultiplier(val x: Float, val y: Float, val z: Float)
data class IntensityShock(val x: Float, val y: Float, val time: Long)
data class ForceBallShock(val max: Float, val min: Float)
data class AreaGoalProb(val xMax: Float, val xMin: Float, val zMax: Float, val zMin: Float)

val STRIKER_GOAL_SHOOTAREA = object {
    val lx = -0.2f
    val rx = 0.195f
    val zmin = 0.07f
    val zmax = 0.1865f
}

val START_HAND_SWIPE_POS = DpOffset(CANVAS_WIDTH_HALF.dp, (CANVAS_HEIGHT_HALF + 200).dp)
val END_HAND_SWIPE_POS = listOf(
    DpOffset((CANVAS_WIDTH_HALF - 250).dp, (CANVAS_HEIGHT_HALF - 200).dp),
    DpOffset(CANVAS_WIDTH_HALF.dp, (CANVAS_HEIGHT_HALF - 200).dp),
    DpOffset((CANVAS_WIDTH_HALF + 250).dp, (CANVAS_HEIGHT_HALF - 200).dp)
)

val BACK_WALL_GOAL_SIZE = Size3D(20.5f, 1f, 7.5f)
val LEFT_RIGHT_WALL_GOAL_SIZE = Size3D(0.1f, 25f, 7.5f)
val UP_WALL_GOAL_SIZE = Size3D(20.5f, 25f, 0.1f)
val BACK_WALL_GOAL_POSITION = Point3D(0f, 155f, -2.7f)
val GOAL_LINE_POS = Point3D(0f, BACK_WALL_GOAL_POSITION.y - UP_WALL_GOAL_SIZE.depth + 2, BACK_WALL_GOAL_POSITION.z)
val POSITION_BALL = Point3D(0.05f, 15.4f, -9f + BALL_RADIUS)
val NUM_AREA_GOAL = object {
    val h = 3
    val w = 5
}
val AREA_GOALS_ANIM = listOf(
    LEFT_UP, SIDE_LEFT_UP, CENTER_UP, SIDE_RIGHT_UP, RIGHT_UP,
    LEFT, SIDE_LEFT, CENTER, SIDE_RIGHT, RIGHT,
    LEFT_DOWN, SIDE_LEFT_DOWN, CENTER_DOWN, SIDE_RIGHT_DOWN, RIGHT_DOWN
)
val GOAL_SPRITE_SWAP_Y = GOAL_LINE_POS.y
val GOAL_SPRITE_SWAP_Z = BACK_WALL_GOAL_POSITION.z + LEFT_RIGHT_WALL_GOAL_SIZE.height
val GOAL_KEEPER_DEPTH_Y = BACK_WALL_GOAL_POSITION.y - UP_WALL_GOAL_SIZE.depth

val POLE_UP_SIZE = SphereSize(0.5f, 0.5f, 40.5f, 10)
val POLE_RIGHT_LEFT_SIZE = SphereSize(0.5f, 0.5f, 15f, 10)

val COLOR_AREA_GOAL = listOf(
    Color(0xFFAA0000), Color(0xFF00FF00), Color(0xFF0000FF), Color(0xFFFFFF00), Color(0xFFFF00FF),
    Color(0xFF00FFFF), Color(0xFFF0F0F0), Color(0xFF0F0F0F), Color(0xFFF000F0), Color(0xFFFFFFFF),
    Color(0xFF567890), Color(0xFFAABBCC), Color(0xFF123456), Color(0xFF888888), Color(0xFF901234)
)

val OFFSET_FIELD_Y = 35f
val OFFSET_FIELD_X = 35f

val FORCE_MULTIPLIER_AXIS = ForceMultiplier(0.12f, 0.4f, 0.08f)

val CALCULATE_PROBABILITY = listOf(
    AreaGoalProb(-7f, -11f, 11f, 8f), AreaGoalProb(-3.6f, -7f, 11f, 8f),
    AreaGoalProb(3.6f, -3.6f, 11f, 8f), AreaGoalProb(7f, 3.6f, 11f, 8f),
    AreaGoalProb(11f, 7f, 11f, 8f), AreaGoalProb(-7f, -7f, 8f, 5f),
    AreaGoalProb(-3.6f, -7f, 8f, 5f), AreaGoalProb(3.6f, -3.6f, 8f, 5f),
    AreaGoalProb(7f, 3.6f, 8f, 5f), AreaGoalProb(11f, 7f, 8f, 5f),
    AreaGoalProb(-7f, -11f, 5f, 0f), AreaGoalProb(-3.6f, -7f, 5f, 0f),
    AreaGoalProb(3.6f, -3.6f, 5f, 0f), AreaGoalProb(7f, 3.6f, 5f, 0f),
    AreaGoalProb(11f, 7f, 5f, 0f)
)

val INTENSITY_DISPLAY_SHOCK = listOf(
    IntensityShock(10f, 7.5f, 50L), IntensityShock(20f, 9f, 50L),
    IntensityShock(30f, 12f, 50L), IntensityShock(33f, 15f, 50L)
)

val FORCE_BALL_DISPLAY_SHOCK = listOf(
    ForceBallShock(55f, MIN_FORCE_Y - 1), ForceBallShock(58f, 55f),
    ForceBallShock(62f, 58f), ForceBallShock(MAX_FORCE_Y, 62f)
)

val CAMERA_POSITION = Point3D(0f, 0f, -7f)
val CAMERA_TEST_LOOK_AT = Point3D(0f, -500f, -100f)

// Flags (will be handled as boolean variables in game state)
const val DISABLE_SOUND_MOBILE = false
const val SHOW_AREAS_GOAL = false
const val SHOW_3D_RENDER = false
const val CAMERA_TEST_TRACKBALL = false
const val CAMERA_TEST_TRANSFORM = false
const val ENABLE_FULLSCREEN = false
const val ENABLE_CHECK_ORIENTATION = false

// Player count
const val NUM_SPRITE_PLAYER = 31

// Helper for Offset
data class Offset(val x: Float, val y: Float)
