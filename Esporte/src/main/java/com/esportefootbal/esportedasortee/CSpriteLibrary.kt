package com.esportefootbal.esportedasortee

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class CSpriteLibrary(private val context: Context) {

    private val sprites = ConcurrentHashMap<String, Bitmap>()
    private val spritePaths = ConcurrentHashMap<String, Int>() // Map sprite name to drawable resource ID
    var totalSpritesToLoad = 0
    var loadedSpritesCount = 0

    private var onImagesLoadedCallback: (() -> Unit)? = null
    private var onAllImagesLoadedCallback: (() -> Unit)? = null

    fun init(onImagesLoaded: () -> Unit, onAllImagesLoaded: () -> Unit) {
        this.onImagesLoadedCallback = onImagesLoaded
        this.onAllImagesLoadedCallback = onAllImagesLoaded
        totalSpritesToLoad = 0
        loadedSpritesCount = 0
        sprites.clear()
        spritePaths.clear()
    }

    fun addSprite(name: String, @DrawableRes resourceId: Int) {
        if (!spritePaths.containsKey(name)) {
            spritePaths[name] = resourceId
            totalSpritesToLoad++
        }
    }

    suspend fun loadSprites() = withContext(Dispatchers.IO) {
        if (totalSpritesToLoad == 0) {
            onAllImagesLoadedCallback?.invoke()
            return@withContext
        }

        spritePaths.forEach { (name, resourceId) ->
            try {
                val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
                if (bitmap != null) {
                    sprites[name] = bitmap
                    loadedSpritesCount++
                    onImagesLoadedCallback?.invoke()
                } else {
                    // Handle error: bitmap could not be decoded
                    println("Failed to decode bitmap for $name (resource ID: $resourceId)")
                }
            } catch (e: Exception) {
                // Handle other exceptions during loading
                println("Error loading sprite $name: ${e.message}")
            }

            if (loadedSpritesCount == totalSpritesToLoad) {
                onAllImagesLoadedCallback?.invoke()
            }
        }
    }

    fun getSprite(name: String): Bitmap? {
        return sprites[name]
    }

    fun getNumSprites(): Int {
        return totalSpritesToLoad
    }
}