package com.example.assignment1

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment1.interfaces.ScoreClickedCallback
import com.example.assignment1.model.ScoreItem
import com.example.assignment1.ui.MapFragment
import com.example.assignment1.ui.ScoreboardFragment
import com.example.assignment1.utilities.BackgroundMusicPlayer

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var main_FRAME_list: FrameLayout
    private lateinit var main_FRAME_map: FrameLayout
    private lateinit var mapFragment: MapFragment
    private lateinit var scoreboardFragment: ScoreboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
    }

    private fun initViews() {
        mapFragment = MapFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment)
            .commit()

        scoreboardFragment = ScoreboardFragment()
        ScoreboardFragment.scoreItemClicked =
            object : ScoreClickedCallback {
                override fun onScoreClicked(score: ScoreItem) {
                    mapFragment.zoomToLocation(score.lat, score.lon)
                }
            }

        scoreboardFragment = ScoreboardFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_list, scoreboardFragment)
            .commit()
    }
}