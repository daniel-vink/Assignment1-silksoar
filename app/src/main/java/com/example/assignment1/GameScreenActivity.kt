package com.example.assignment1

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment1.logic.GameManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.util.Timer
import java.util.TimerTask
import androidx.core.view.isVisible
import com.example.assignment1.interfaces.TiltCallback
import com.example.assignment1.utilities.BackgroundMusicPlayer
import com.example.assignment1.utilities.Constants
import com.example.assignment1.utilities.SignalManager
import com.example.assignment1.utilities.SingleSoundPlayer
import com.example.assignment1.utilities.TiltDetector
import com.google.android.material.textview.MaterialTextView
import kotlin.math.log

private lateinit var game_IMG_enemy: Array<Array<AppCompatImageView>>
private lateinit var game_IMG_hornet: Array<AppCompatImageView>
private lateinit var game_IMG_masks: Array<AppCompatImageView>
private lateinit var game_LBL_score: MaterialTextView
private lateinit var game_FAB_right: ExtendedFloatingActionButton
private lateinit var game_FAB_left: ExtendedFloatingActionButton
private lateinit var gameManager: GameManager
private lateinit var timer: Timer
private var hornetPosition: Int = 2
private var enemySkip: Boolean = false
private var randomSpawn: Int = 0
private var randomMaskSpawn: Int = 0
private var score: Int = 0
private var speed: Long = 1000L
private lateinit var tiltDetector: TiltDetector
private var isSensorMode: Boolean = false



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
        SignalManager.init(this)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        restartTimer()
        if(isSensorMode)
            tiltDetector.start()
        BackgroundMusicPlayer.getInstance().playMusic()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        if(isSensorMode)
            tiltDetector.stop()
        BackgroundMusicPlayer.getInstance().pauseMusic()
    }

    private fun findViews() {
        game_IMG_enemy = arrayOf(
            arrayOf(
                findViewById(R.id.game_IMG_image00),
                findViewById(R.id.game_IMG_image01),
                findViewById(R.id.game_IMG_image02),
                findViewById(R.id.game_IMG_image03),
                findViewById(R.id.game_IMG_image04)),
            arrayOf(
                findViewById(R.id.game_IMG_image10),
                findViewById(R.id.game_IMG_image11),
                findViewById(R.id.game_IMG_image12),
                findViewById(R.id.game_IMG_image13),
                findViewById(R.id.game_IMG_image14)),
            arrayOf(
                findViewById(R.id.game_IMG_image20),
                findViewById(R.id.game_IMG_image21),
                findViewById(R.id.game_IMG_image22),
                findViewById(R.id.game_IMG_image23),
                findViewById(R.id.game_IMG_image24)),
            arrayOf(
                findViewById(R.id.game_IMG_image30),
                findViewById(R.id.game_IMG_image31),
                findViewById(R.id.game_IMG_image32),
                findViewById(R.id.game_IMG_image33),
                findViewById(R.id.game_IMG_image34)),
            arrayOf(
                findViewById(R.id.game_IMG_image40),
                findViewById(R.id.game_IMG_image41),
                findViewById(R.id.game_IMG_image42),
                findViewById(R.id.game_IMG_image43),
                findViewById(R.id.game_IMG_image44)),
            arrayOf(
                findViewById(R.id.game_IMG_image50),
                findViewById(R.id.game_IMG_image51),
                findViewById(R.id.game_IMG_image52),
                findViewById(R.id.game_IMG_image53),
                findViewById(R.id.game_IMG_image54)),
            arrayOf(
                findViewById(R.id.game_IMG_image60),
                findViewById(R.id.game_IMG_image61),
                findViewById(R.id.game_IMG_image62),
                findViewById(R.id.game_IMG_image63),
                findViewById(R.id.game_IMG_image64)))

        game_IMG_hornet = arrayOf(
            findViewById(R.id.game_IMG_image60),
            findViewById(R.id.game_IMG_image61),
            findViewById(R.id.game_IMG_image62),
            findViewById(R.id.game_IMG_image63),
            findViewById(R.id.game_IMG_image64))

        game_IMG_masks = arrayOf(
            findViewById(R.id.game_IMG_mask0),
            findViewById(R.id.game_IMG_mask1),
            findViewById(R.id.game_IMG_mask2))

        game_LBL_score = findViewById(R.id.game_LBL_score)

        game_FAB_right = findViewById(R.id.game_FAB_right)
        game_FAB_left = findViewById(R.id.game_FAB_left)
    }

    private fun initViews() {
        val bundle : Bundle? = intent.extras
        hornetPosition = 2
        score = 0
        speed = 1000L
        enemySkip = false
        if(bundle != null){
            speed = gameManager.getSpeed(bundle.getInt(Constants.BundleKeys.GAME_MODE_KEY))
        }
        game_IMG_hornet[hornetPosition].visibility = View.VISIBLE
        if(bundle?.getInt(Constants.BundleKeys.GAME_MODE_KEY) == 2)
        {
            isSensorMode = true
            initTiltDetector()
            game_FAB_left.hide()
            game_FAB_right.hide()
        }
        else {
            game_FAB_left.setOnClickListener { View -> moveLeft() }
            game_FAB_right.setOnClickListener { View -> moveRight() }
        }
    }

    private fun startTimer(speed: Long) {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                Log.d("Timer Runnable", "")
                runOnUiThread {
                    updateUI()
                }
            }
        },0, speed)
    }

    private fun restartTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
        startTimer(speed)
    }

    private fun moveLeft() {
        if(hornetPosition > 0)
        {
            if(game_IMG_hornet[hornetPosition - 1].isVisible)
                updateMasks(game_IMG_hornet[hornetPosition - 1].tag)
            else
                game_IMG_hornet[hornetPosition - 1].visibility = View.VISIBLE

            game_IMG_hornet[hornetPosition].visibility = View.INVISIBLE
            hornetPosition--
            game_IMG_hornet[hornetPosition].setImageResource(R.drawable.floating_hornet)
            game_IMG_hornet[hornetPosition].visibility = View.VISIBLE
        }
    }
    private fun moveRight() {
        val hornetBound = game_IMG_hornet.size - 1
        if(hornetPosition < hornetBound)
        {
            if(game_IMG_hornet[hornetPosition + 1].isVisible)
                updateMasks(game_IMG_hornet[hornetPosition + 1].tag)
            else
                game_IMG_hornet[hornetPosition + 1].visibility = View.VISIBLE

            game_IMG_hornet[hornetPosition].visibility = View.INVISIBLE
            hornetPosition++
            game_IMG_hornet[hornetPosition].setImageResource(R.drawable.floating_hornet)
            game_IMG_hornet[hornetPosition].visibility = View.VISIBLE

        }
    }
    private fun updateUI(){
        val hornetBound = game_IMG_hornet.size - 1
        
        for(i in 6 downTo 0){
            for(j in 0..hornetBound){
                if(i == 6 && hornetPosition != j)
                    game_IMG_enemy[i][j].visibility = View.INVISIBLE

                if(game_IMG_enemy[i][j].isVisible && i < 6){
                    if(game_IMG_enemy[i + 1][j].isVisible){
                        updateMasks(game_IMG_enemy[i][j].tag)
                    }
                    game_IMG_enemy[i][j].visibility = View.INVISIBLE
                    game_IMG_enemy[i + 1][j].visibility = View.VISIBLE
                    game_IMG_enemy[i + 1][j].setImageDrawable(game_IMG_enemy[i][j].drawable)
                    game_IMG_enemy[i + 1][j].tag = game_IMG_enemy[i][j].tag
                    
                    // Restore hornet if it was stepped on
                    if (i + 1 == 6 && j == hornetPosition) {
                        game_IMG_hornet[j].setImageResource(R.drawable.floating_hornet)
                    }
                }
            }
        }
        randomMaskSpawn = (0 .. 10).random()
        if(randomMaskSpawn == 10){
            randomSpawn = (0..hornetBound).random()
            game_IMG_enemy[0][randomSpawn].setImageResource(R.drawable.mask_life)
            game_IMG_enemy[0][randomSpawn].tag = 1
            game_IMG_enemy[0][randomSpawn].visibility = View.VISIBLE
        }
        
        if(!enemySkip) {
            randomSpawn = (0..hornetBound).random()
            if(game_IMG_enemy[0][randomSpawn].isVisible){
                if(randomSpawn == hornetBound)
                    randomSpawn--
                else
                    randomSpawn++
            }
            game_IMG_enemy[0][randomSpawn].setImageResource(R.drawable.enemy)
            game_IMG_enemy[0][randomSpawn].tag = 0
            game_IMG_enemy[0][randomSpawn].visibility = View.VISIBLE
            enemySkip = true
        }
        else
            enemySkip = false

        score += Constants.GameLogic.SCORE_DEFAULT
        game_LBL_score.text = "Score: $score"

    }

    private fun updateMasks(tag: Any) {
        val ssp = SingleSoundPlayer(this)
        ssp.playSound(R.raw.hit)
        if(tag == 0) {
            gameManager.gotHit()
        }
        if(tag == 1) {
            if(gameManager.numOfHits > 0)
                game_IMG_masks[game_IMG_masks.size - gameManager.numOfHits].visibility = View.VISIBLE
            gameManager.gotHealth()
        }
        if(gameManager.isGameOver){
            timer.cancel()
            changeActivity()
        }
        if(gameManager.numOfHits != 0){
            game_IMG_masks[game_IMG_masks.size - gameManager.numOfHits].visibility = View.INVISIBLE
        }
    }

    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            this,
            object : TiltCallback {
                override fun tiltRight() {
                    moveRight()
                }

                override fun tiltLeft() {
                    moveLeft()
                }

                override fun tiltForward() {
                    if (speed != 500L) {
                        speed = 500L
                        restartTimer()
                    }
                }

                override fun tiltBackward() {
                    if (speed != 1000L) {
                        speed = 1000L
                        restartTimer()
                    }
                }

            }
        )
    }

    private fun changeActivity() {
        val intent = Intent(this, GameOverActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(Constants.BundleKeys.SCORE_KEY, score)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}
