package com.example.assignment1.logic

import android.widget.ImageView
import androidx.core.widget.ImageViewCompat

class GameManager (private val lifeCount: Int = 3){

    var numOfHits: Int = 0
        private set

    val isGameOver: Boolean
        get() = numOfHits == lifeCount

    fun gotHit(){
        numOfHits++
    }
}