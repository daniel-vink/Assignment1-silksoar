package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment1.utilities.BackgroundMusicPlayer
import com.example.assignment1.utilities.Constants
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

private lateinit var main_BTN_regular: MaterialButton
private lateinit var main_BTN_fast: MaterialButton
private lateinit var main_BTN_sensors: MaterialButton
private lateinit var main_BTN_leaderboard: MaterialButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        requestLocationPermission()
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
        main_BTN_regular = findViewById(R.id.main_BTN_regular)
        main_BTN_fast = findViewById(R.id.main_BTN_fast)
        main_BTN_sensors = findViewById(R.id.main_BTN_sensors)
        main_BTN_leaderboard = findViewById(R.id.main_BTN_leaderboard)
    }

    private fun initViews() {
        main_BTN_regular.setOnClickListener { view -> changeView(Constants.GameModes.NORMAL) }
        main_BTN_fast.setOnClickListener { view -> changeView(Constants.GameModes.FAST) }
        main_BTN_sensors.setOnClickListener { view -> changeView(Constants.GameModes.SENSORS) }
        main_BTN_leaderboard.setOnClickListener { view -> changeViewLeaderboard() }
    }

    private fun changeView(key: Int) {
        val intent = Intent(this, GameScreenActivity::class.java)
        var bundle = Bundle()
        bundle.putInt(Constants.BundleKeys.GAME_MODE_KEY, key)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun changeViewLeaderboard() {
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun requestLocationPermission() {ActivityCompat.requestPermissions(
        this,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
        100 // Request code
    )
    }
}




