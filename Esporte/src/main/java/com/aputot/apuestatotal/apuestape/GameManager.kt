//package com.aputot.apuestatotal.apuestape
//
//import android.content.Context
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//object GameManager {
//    lateinit var context: Context
//    lateinit var spriteLibrary: CSpriteLibrary
//
//    var gameState: MutableState<Int> = mutableStateOf(STATE_LOADING)
//
//    fun init(appContext: Context) {
//        context = appContext
//        spriteLibrary = CSpriteLibrary(context)
//    }
//
//    suspend fun loadGameAssets() = withContext(Dispatchers.IO) {
//        spriteLibrary.init(
//            onImagesLoaded = {
//                // Update loading progress for game assets
//                // This might need to be a separate state in GameManager if we want to show progress for game assets
//            },
//            onAllImagesLoaded = {
//                // All game assets loaded, transition to menu
//                CoroutineScope(Dispatchers.Main).launch {
//                    gameState.value = STATE_MENU
//                }
//            }
//        )
//
//        // Add all game sprites here
//        spriteLibrary.addSprite("but_play", R.drawable.but_play)
//        spriteLibrary.addSprite("but_exit", R.drawable.but_exit)
//        spriteLibrary.addSprite("bg_menu", R.drawable.bg_menu)
//        spriteLibrary.addSprite("bg_game", R.drawable.bg_game)
//        spriteLibrary.addSprite("msg_box", R.drawable.msg_box)
//        spriteLibrary.addSprite("audio_icon", R.drawable.audio_icon)
//        spriteLibrary.addSprite("but_home", R.drawable.but_home)
//        spriteLibrary.addSprite("but_restart", R.drawable.but_restart)
//        spriteLibrary.addSprite("but_fullscreen", R.drawable.but_fullscreen)
//        spriteLibrary.addSprite("ball", R.drawable.ball)
//        spriteLibrary.addSprite("but_level", R.drawable.but_level)
//        spriteLibrary.addSprite("but_continue", R.drawable.but_continue)
//        spriteLibrary.addSprite("but_yes", R.drawable.but_yes)
//        spriteLibrary.addSprite("but_no", R.drawable.but_no)
//        spriteLibrary.addSprite("but_info", R.drawable.but_info)
//        //spriteLibrary.addSprite("logo_ctl", R.drawable.logo_ctl)
//        spriteLibrary.addSprite("but_pause", R.drawable.but_pause)
//        spriteLibrary.addSprite("arrow_right", R.drawable.arrow_right)
//        spriteLibrary.addSprite("arrow_left", R.drawable.arrow_left)
//        spriteLibrary.addSprite("ball_shadow", R.drawable.ball_shadow)
//        spriteLibrary.addSprite("start_ball", R.drawable.start_ball)
//        spriteLibrary.addSprite("hand_touch", R.drawable.hand_touch)
//        spriteLibrary.addSprite("cursor", R.drawable.cursor)
//        spriteLibrary.addSprite("shot_left", R.drawable.shot_left)
//        spriteLibrary.addSprite("goal", R.drawable.goal)
//
//        // Player sprites
//        for (i in 0 until NUM_SPRITE_PLAYER) {
//            spriteLibrary.addSprite("player_$i", context.resources.getIdentifier("player_$i", "drawable", context.packageName))
//        }
//
//        // Goalkeeper sprites
//        SPRITE_NAME_GOALKEEPER.forEachIndexed { index, namePrefix ->
//            for (i in 0 until NUM_SPRITE_GOALKEEPER[index]) {
//                spriteLibrary.addSprite(namePrefix + i, context.resources.getIdentifier("${namePrefix}_$i", "drawable", context.packageName))
//            }
//        }
//
//        spriteLibrary.loadSprites()
//    }
//
//    fun gotoMenu() {
//        gameState.value = STATE_MENU
//    }
//
//    fun gotoGame() {
//        gameState.value = STATE_GAME
//    }
//
//    fun gotoHelp() {
//        gameState.value = STATE_HELP
//    }
//
//    fun stopUpdate() {
//        // Pause game loop
//    }
//
//    fun startUpdate() {
//        // Resume game loop
//    }
//}
