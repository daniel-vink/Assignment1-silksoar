package com.example.assignment1.interfaces

import com.example.assignment1.model.ScoreItem

interface ScoreClickedCallback {
    fun onScoreClicked(score: ScoreItem)
}