package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

private lateinit var main_BTN_start: MaterialButton

private lateinit var main_BTN_about: MaterialButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        findViews()
    }

    private fun initViews() {
        main_BTN_start = findViewById(R.id.main_BTN_start)
        main_BTN_about = findViewById(R.id.main_BTN_about)
    }

    private fun findViews() {
        main_BTN_start.setOnClickListener { view -> changeView() }
    }

    private fun changeView() {
        val intent = Intent(this, GameScreenActivity::class.java)
        startActivity(intent)
        finish()
    }
}


