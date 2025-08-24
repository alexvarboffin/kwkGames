package com.olimpfootball.olimpbet.footgame

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.olimpfootball.olimpbet.footgame.data.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException

class SoundManager(private val context: Context, private val userPreferencesRepository: UserPreferencesRepository) {

    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null

    private val soundMap = mutableMapOf<String, Int>()
    private var isMuted = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        loadSound("btn.webm")
        loadSound("game-over.webm")
        loadSound("kick.webm")
        loadSound("kick2.webm")

        CoroutineScope(Dispatchers.Main).launch {
            isMuted = userPreferencesRepository.preferencesFlow.first()
        }
    }

    private fun loadSound(fileName: String) {
        try {
            val assetManager = context.assets
            val descriptor = assetManager.openFd(fileName)
            val soundId = soundPool?.load(descriptor, 1)
            if (soundId != null) {
                soundMap[fileName] = soundId
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun playSound(fileName: String) {
        if (!isMuted) {
            val soundId = soundMap[fileName]
            if (soundId != null) {
                soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
            }
        }
    }

    fun startBackgroundMusic() {
        if (mediaPlayer == null) {
            try {
                val assetManager = context.assets
                val descriptor = assetManager.openFd("mainbgm.ogg")
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                    isLooping = true
                    prepare()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (!isMuted) {
            mediaPlayer?.start()
        }
    }

    fun pauseBackgroundMusic() {
        mediaPlayer?.pause()
    }

    fun release() {
        soundPool?.release()
        soundPool = null
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun toggleMute() {
        isMuted = !isMuted
        if (isMuted) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
        CoroutineScope(Dispatchers.Main).launch {
            userPreferencesRepository.setIsMusicOn(isMuted)
        }
    }
}
