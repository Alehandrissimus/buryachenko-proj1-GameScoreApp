package com.example.buryachenko_proj1_gamescoreapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TeamsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        setupListeners()
    }

    private fun setupListeners() {
        teams_button_cancel.setOnClickListener {
            this.finish()
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val team1 = teams_edittext_team1.text.toString()
                val team2 = teams_edittext_team2.text.toString()
                val time = teams_edittext_time.text.toString()

                teams_button_start.isEnabled =
                    team1.isNotEmpty() && team2.isNotEmpty() && time.isNotEmpty()

                if (team1.isEmpty() || team2.isEmpty() || time.isEmpty()) {
                    teams_button_start.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
        teams_edittext_team1.addTextChangedListener(textWatcher)
        teams_edittext_team2.addTextChangedListener(textWatcher)
        teams_edittext_time.addTextChangedListener(textWatcher)

        teams_button_start.setOnClickListener {
            val time = teams_edittext_time.text.toString().toInt()
            val team1 = teams_edittext_team1.text.toString()
            val team2 = teams_edittext_team2.text.toString()
            ScoreActivity.start(this, time, team1, team2)
        }
    }
}