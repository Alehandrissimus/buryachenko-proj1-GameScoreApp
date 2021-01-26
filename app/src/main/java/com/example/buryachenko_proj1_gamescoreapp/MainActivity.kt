package com.example.buryachenko_proj1_gamescoreapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
    }

    private fun setupListeners() {
        mainButtonStart.setOnClickListener {
            TeamsActivity.start(this)
        }
        mainButtonLeaderboards.setOnClickListener {
            LeaderboardActivity.start(this)
        }
    }
}