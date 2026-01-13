package com.example.assignment1.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.MainActivity
import com.example.assignment1.R
import com.example.assignment1.adapters.ScoreAdapter
import com.example.assignment1.interfaces.ScoreClickedCallback
import com.example.assignment1.utilities.ScoreManager
import com.google.android.material.button.MaterialButton


class ScoreboardFragment : Fragment() {

    private lateinit var scoreboard_LST_scores: RecyclerView
    private lateinit var scoreboard_BTN_backToMenu: MaterialButton

    private lateinit var scoreManager: ScoreManager

    //var scoreClickedCallback: ScoreClickedCallback? = null
    var scoreItemClicked: ScoreClickedCallback? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = FragmentScoreboardBinding.inflate(inflater, container, false)

        val v: View = inflater.inflate(R.layout.fragment_scoreboard, container, false)
        scoreManager = ScoreManager(requireContext())
        findViews(v)
        initViews(v)
        return v
    }

    private fun findViews(v: View) {
        scoreboard_BTN_backToMenu = v.findViewById(R.id.scoreboard_BTN_backToMenu)
        scoreboard_LST_scores = v.findViewById(R.id.scoreboard_LST_scores)
    }

    private fun initViews(v: View) {
        scoreboard_BTN_backToMenu.setOnClickListener { view -> changeView() }
        val topScores = scoreManager.getScores()
        val adapter = ScoreAdapter(topScores)
        adapter.scoreClickedCallback = this.scoreItemClicked

        scoreboard_LST_scores.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        scoreboard_LST_scores.adapter = adapter
        }
    private fun changeView() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()

    }
}