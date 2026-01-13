package com.example.assignment1.utilities

import androidx.core.R

class Constants {
    object GameLogic {
        const val SCORE_DEFAULT: Int = 10
    }

    object BundleKeys {
        const val NAME_KEY: String = "NAME_KEY"
        const val SCORE_KEY: String = "SCORE_KEY"
        const val GAME_MODE_KEY: String = "GAME_MODE_KEY"
    }

    object GameModes {
        const val NORMAL: Int = 0
        const val FAST: Int = 1
        const val SENSORS: Int = 2
    }
}