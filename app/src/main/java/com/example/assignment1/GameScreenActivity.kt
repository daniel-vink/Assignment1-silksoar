package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment1.logic.GameManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.util.Timer
import java.util.TimerTask

private lateinit var game_IMG_enemy: Array<Array<AppCompatImageView>>
private lateinit var game_IMG_hornet: Array<AppCompatImageView>
private lateinit var game_IMG_masks: Array<AppCompatImageView>
private lateinit var game_FAB_right: ExtendedFloatingActionButton
private lateinit var game_FAB_left: ExtendedFloatingActionButton

private lateinit var gameManager: GameManager
private lateinit var timer: Timer
private var hornetPosition: Int = 1
private var enemySkip: Boolean = false
private var randomSpawn: Int = 0

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
        gameManager = GameManager(game_IMG_masks.size)
        initViews()
    }

    private fun findViews() {
        game_IMG_enemy = arrayOf(
            arrayOf(
                findViewById(R.id.game_IMG_image00),
                findViewById(R.id.game_IMG_image01),
                findViewById(R.id.game_IMG_image02)),
            arrayOf(findViewById(R.id.game_IMG_image10),
                findViewById(R.id.game_IMG_image11),
                findViewById(R.id.game_IMG_image12)),
            arrayOf(findViewById(R.id.game_IMG_image20),
                findViewById(R.id.game_IMG_image21),
                findViewById(R.id.game_IMG_image22)),
            arrayOf(findViewById(R.id.game_IMG_image30),
                findViewById(R.id.game_IMG_image31),
                findViewById(R.id.game_IMG_image32)),
            arrayOf(findViewById(R.id.game_IMG_image40),
                findViewById(R.id.game_IMG_image41),
                findViewById(R.id.game_IMG_image42)),
            arrayOf(findViewById(R.id.game_IMG_image50),
                findViewById(R.id.game_IMG_image51),
                findViewById(R.id.game_IMG_image52)))

        game_IMG_hornet = arrayOf(
            findViewById(R.id.game_IMG_image50),
            findViewById(R.id.game_IMG_image51),
            findViewById(R.id.game_IMG_image52))

        game_IMG_masks = arrayOf(
            findViewById(R.id.game_IMG_mask0),
            findViewById(R.id.game_IMG_mask1),
            findViewById(R.id.game_IMG_mask2))

        game_FAB_right = findViewById(R.id.game_FAB_right)
        game_FAB_left = findViewById(R.id.game_FAB_left)
    }

    private fun initViews() {
        game_IMG_hornet[hornetPosition].visibility = View.VISIBLE
        game_FAB_left.setOnClickListener { View -> moveLeft() }
        game_FAB_right.setOnClickListener { View -> moveRight() }
        startTimer()
    }

    private fun startTimer() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                Log.d("Timer Runnable", "")
                runOnUiThread {
                    updateUI()
                }
            }
        },0, 1000)
    }

    private fun moveLeft() {
        if(hornetPosition > 0)
        {
            if(game_IMG_hornet[hornetPosition - 1].visibility == View.VISIBLE)
                updateMasks()
            else
                game_IMG_hornet[hornetPosition - 1].visibility = View.VISIBLE

            game_IMG_hornet[hornetPosition].setImageResource(R.drawable.enemy)
            game_IMG_hornet[hornetPosition].visibility = View.INVISIBLE
            hornetPosition--
            game_IMG_hornet[hornetPosition].setImageResource(R.drawable.floating_hornet)
        }
    }
    private fun moveRight() {
        if(hornetPosition < 2)
        {
            if(game_IMG_hornet[hornetPosition + 1].visibility == View.VISIBLE)
                updateMasks()
            else
                game_IMG_hornet[hornetPosition + 1].visibility = View.VISIBLE
            game_IMG_hornet[hornetPosition].setImageResource(R.drawable.enemy)
            game_IMG_hornet[hornetPosition].visibility = View.INVISIBLE
            hornetPosition++
            game_IMG_hornet[hornetPosition].setImageResource(R.drawable.floating_hornet)

        }
    }
    private fun updateUI(){
        for(i in 5 downTo 0){
            for(j in 0..2){
                if(i == 5 && hornetPosition != j)
                    game_IMG_enemy[i][j].visibility = View.INVISIBLE

                if(game_IMG_enemy[i][j].visibility == View.VISIBLE && i < 5){
                    if(game_IMG_enemy[i + 1][j].visibility == View.VISIBLE){
                        updateMasks()
                    }
                    game_IMG_enemy[i][j].visibility = View.INVISIBLE
                    game_IMG_enemy[i + 1][j].visibility = View.VISIBLE
                }
            }
        }
        if(!enemySkip) {
            randomSpawn = (0..2).random()
            game_IMG_enemy[0][randomSpawn].visibility = View.VISIBLE
            enemySkip = true
        }
        else
            enemySkip = false
    }

    private fun updateMasks(){
        gameManager.gotHit()
        if(gameManager.isGameOver){
            timer.cancel()
            changeActivity()
        }
        if(gameManager.numOfHits != 0){
            game_IMG_masks[game_IMG_masks.size - gameManager.numOfHits].visibility = View.INVISIBLE
        }
    }

    private fun changeActivity() {
        val intent = Intent(this, GameOverActivity::class.java)
        startActivity(intent)
        finish()
    }
}
