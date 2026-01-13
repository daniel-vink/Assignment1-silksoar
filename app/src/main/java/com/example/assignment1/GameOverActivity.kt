package com.example.assignment1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment1.model.ScoreItem
import com.example.assignment1.utilities.BackgroundMusicPlayer
import com.example.assignment1.utilities.Constants
import com.example.assignment1.utilities.ScoreManager
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class GameOverActivity : AppCompatActivity() {
    private lateinit var gameover_BTN_back: MaterialButton
    private lateinit var gameOver_LBL_text: MaterialTextView
    private lateinit var gameOver_ET_name: AppCompatEditText
    private lateinit var fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findViews()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        BackgroundMusicPlayer.getInstance().playMusic()
    }

    override fun onPause() {
        super.onPause()
        BackgroundMusicPlayer.getInstance().pauseMusic()
    }

    private fun findViews() {
        gameover_BTN_back = findViewById(R.id.gameover_BTN_backToMenu)
        gameOver_LBL_text = findViewById(R.id.gameOver_LBL_text)
        gameOver_ET_name = findViewById(R.id.gameOver_ET_name)
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val bundle : Bundle? = intent.extras
        gameOver_LBL_text.text = "Game Over! \nYour score was: ${bundle?.getInt(Constants.BundleKeys.SCORE_KEY)}"
        gameover_BTN_back.setOnClickListener { view -> changeView() }
    }

    private fun changeView() {
        saveFinalScore()
    }

    private fun saveFinalScore() {
        val playerName = gameOver_ET_name.text.toString().ifEmpty { "Anonymous" }
        val finalScore = intent.getIntExtra(Constants.BundleKeys.SCORE_KEY, 0)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val lat = location?.latitude ?: 0.0
                val lon = location?.longitude ?: 0.0

                val newScoreEntry = ScoreItem.Builder()
                    .name(playerName)
                    .score(finalScore)
                    .lat(lat)
                    .lon(lon)
                    .build()

                ScoreManager(this).addScore(newScoreEntry)

                // MOVE NAVIGATION HERE:
                navigateToLeaderboard()
            }
        } else {
            val newScoreEntry = ScoreItem.Builder()
                .name(playerName)
                .score(finalScore)
                .lat(0.0)
                .lon(0.0)
                .build()
            ScoreManager(this).addScore(newScoreEntry)
        }
    }

    private fun navigateToLeaderboard() {
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
        finish()
    }

//    private fun saveFinalScore() {
//        val playerName = gameOver_ET_name.text.toString().ifEmpty { "Anonymous" }
//
//        val finalScore = intent.getIntExtra(Constants.BundleKeys.SCORE_KEY, 0)
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                val lat = location?.latitude ?: 0.0
//                val lon = location?.longitude ?: 0.0
//
//                val newScoreEntry = ScoreItem.Builder()
//                    .name(playerName)
//                    .score(finalScore)
//                    .lat(lat)
//                    .lon(lon)
//                    .build()
//
//                ScoreManager(this).addScore(newScoreEntry)
//            }
//        } else {
//            val newScoreEntry = ScoreItem.Builder()
//                .name(playerName)
//                .score(finalScore)
//                .lat(0.0)
//                .lon(0.0)
//                .build()
//            val scoreManager = ScoreManager(this)
//            scoreManager.addScore(newScoreEntry)
//        }
//    }
}