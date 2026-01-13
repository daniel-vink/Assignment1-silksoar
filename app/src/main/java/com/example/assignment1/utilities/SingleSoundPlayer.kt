package com.example.assignment1.utilities

import android.content.Context
import android.media.MediaPlayer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SingleSoundPlayer(context: Context){
    private val context: Context = context.applicationContext
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun playSound(resourceId: Int){
        executor.execute {
            val mediaPlayer = MediaPlayer.create(
                context,
                resourceId
            )

            mediaPlayer.isLooping = false
            mediaPlayer.setVolume(0.25f,0.25f)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                    mp: MediaPlayer? ->
                var mpl = mp
                mpl?.stop()
                mpl?.release()
                mpl = null
            }
        }
    }
}