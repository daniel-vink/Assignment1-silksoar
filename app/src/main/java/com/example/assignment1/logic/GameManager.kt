package com.example.assignment1.logic

import com.example.assignment1.utilities.SignalManager

class GameManager (private val lifeCount: Int = 3){

    var numOfHits: Int = 0
        private set

    val isGameOver: Boolean
        get() = numOfHits == lifeCount

    fun gotHit(){
        if(numOfHits < 2) {
            SignalManager.getInstance().toast(
                "SHAW!", SignalManager.ToastLength.SHORT
            )
        }
        else{
            SignalManager.getInstance().toast(
                "You Died!", SignalManager.ToastLength.LONG
            )
        }
        SignalManager.getInstance().vibrate()
        numOfHits++
    }
}