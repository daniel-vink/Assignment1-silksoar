package com.example.assignment1

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.WindowMetrics
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

private lateinit var game_IMG_hornet: AppCompatImageView

private lateinit var game_IMG_enemyLeft: AppCompatImageView

private lateinit var game_IMG_enemyMiddle: AppCompatImageView

private lateinit var game_IMG_enemyRight: AppCompatImageView

private lateinit var game_FAB_right: ExtendedFloatingActionButton

class GameScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
    }

    private fun findViews() {
        game_IMG_hornet = findViewById(R.id.game_IMG_hornet)
        game_FAB_right = findViewById(R.id.game_FAB_right)
    }

    private fun initViews() {
        game_FAB_right.setOnClickListener { View -> moveImage() }
    }
    private fun moveImage() {
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val width = bounds.width()
        val height = bounds.height()

        val moveHeight = (height/10).toFloat()
        game_IMG_hornet.animate()
            .translationYBy(moveHeight)
            .start()
    }
}
