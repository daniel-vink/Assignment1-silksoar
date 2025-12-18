package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class GameOverActivity : AppCompatActivity() {
    private lateinit var gameover_BTN_back: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
    }

    private fun findViews() {
        gameover_BTN_back = findViewById(R.id.gameover_BTN_backToMenu)
    }

    private fun initViews() {
        gameover_BTN_back.setOnClickListener { view -> changeView() }
    }

    private fun changeView() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}