package com.example.assignment1.utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.assignment1.model.ScoreItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class ScoreManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("HighScores", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addScore(newScore: ScoreItem) {
        val scores = getScores().toMutableList()
        scores.add(newScore)
        val topTen = scores.sortedByDescending { it.score }.take(10)

        // 2. Save back to SharedPreferences
        val json = gson.toJson(topTen)
        sharedPreferences.edit { putString("score_list", json) }
    }

    fun getScores(): List<ScoreItem> {
        val json = sharedPreferences.getString("score_list", null) ?: return emptyList()
        val type = object : TypeToken<List<ScoreItem>>() {}.type
        return gson.fromJson(json, type)
    }
}