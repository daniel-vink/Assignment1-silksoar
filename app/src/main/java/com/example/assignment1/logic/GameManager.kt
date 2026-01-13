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

    fun gotHealth(){
        if(numOfHits > 0) {
            SignalManager.getInstance().toast(
                "Got Health!", SignalManager.ToastLength.SHORT
            )
            numOfHits--
        }
        else{
            SignalManager.getInstance().toast(
                "Masks full!", SignalManager.ToastLength.SHORT
            )
        }
    }

    fun getSpeed(key: Int): Long {
        if(key == 1)
            return 500L
        return 1000L
    }
}