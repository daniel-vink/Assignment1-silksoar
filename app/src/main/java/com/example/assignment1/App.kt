package com.example.assignment1

import android.app.Application
import com.example.assignment1.utilities.BackgroundMusicPlayer

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        BackgroundMusicPlayer.init(this)
        BackgroundMusicPlayer.getInstance().setResourceId(R.raw.ost_background_music)

    }

    override fun onTerminate() {
        super.onTerminate()
        BackgroundMusicPlayer.getInstance().stopMusic()
    }
}