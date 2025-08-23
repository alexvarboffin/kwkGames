package com.olimpfootball.olimpbet.footgame

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import java.io.IOException

class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<String, Int>()

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        loadSounds()
    }

    private fun loadSounds() {
        val soundFiles = listOf("btn.webm", "game-over.webm", "kick.webm", "kick2.webm")
        soundFiles.forEach { fileName ->
            try {
                val afd = context.assets.openFd(fileName)
                val soundId = soundPool?.load(afd, 1)
                if (soundId != null) {
                    soundMap[fileName] = soundId
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun playSound(fileName: String) {
        val soundId = soundMap[fileName]
        soundId?.let {
            soundPool?.play(it, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}