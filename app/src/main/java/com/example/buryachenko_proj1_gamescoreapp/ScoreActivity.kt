package com.example.buryachenko_proj1_gamescoreapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_score.*
import java.util.*

class ScoreActivity : AppCompatActivity() {

    companion object {
        private const val TIME_KEY = "TIME_KEY"
        private val TEAM1_KEY = "TEAM1_KEY"
        private val TEAM2_KEY = "TEAM2_KEY"

        fun start(context: Context, time: Int, team1: String, team2: String) {
            val intent = Intent(context, ScoreActivity::class.java)
            intent.putExtra(TIME_KEY, time)
            intent.putExtra(TEAM1_KEY, team1)
            intent.putExtra(TEAM2_KEY, team2)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        setupUI()
        setupListeners()
    }


    private var timeLeft = 0L
    private var timer: CountDownTimer? = null
    private var timerIsPaused = false

    private fun setupListeners() {
        timeLeft = ((intent.getIntExtra(TIME_KEY, 1)) * 60).toLong()

        score_cancel.setOnClickListener {
            timer?.cancel()
            this.finish()
        }
        score_start.setOnClickListener {
            score_button_addScore1.isEnabled = true
            score_button_addScore2.isEnabled = true
            score_start.isEnabled = false
            score_button_pause.isEnabled = true
            setupTimer()
        }

        var team1Score = 0
        var team2Score = 0
        score_button_addScore1.setOnClickListener {
            team1Score += 1
            score_textView_team1Score.text =
                resources.getString(R.string.team_score_template, team1Score)
        }
        score_button_addScore2.setOnClickListener {
            team2Score += 1
            score_textView_team2Score.text =
                resources.getString(R.string.team_score_template, team2Score)
        }

        score_button_pause.setOnClickListener {
            if (!timerIsPaused) {
                timerIsPaused = true
                score_button_pause.isEnabled = false
                score_button_addScore1.isEnabled = false
                score_button_addScore2.isEnabled = false
                score_button_pause.text = getString(R.string.continuee)
            } else {
                timerIsPaused = false
                score_button_addScore1.isEnabled = true
                score_button_addScore2.isEnabled = true
                setupTimer()
                score_button_pause.text = getString(R.string.pause)
            }
        }

        score_button_continue.setOnClickListener {
            val time = intent.getIntExtra(TIME_KEY, 1)
            val team1 = intent.getStringExtra(TEAM1_KEY)
            val team2 = intent.getStringExtra(TEAM2_KEY)
            if (team1 != null && team2 != null) {
                val result = Score(time, team1, team1Score, team2, team2Score)
                WinnerActivity.start(this, result)
            }
        }
    }

    private fun setupTimer() {
        timeLeft++
        timer = object : CountDownTimer((timeLeft * 1000L), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!timerIsPaused) {
                    updateUI(millisUntilFinished)
                } else {
                    timeLeft = millisUntilFinished / 1000
                    score_button_pause.isEnabled = true
                    cancel()
                }
            }

            override fun onFinish() {
                Toast.makeText(this@ScoreActivity, "Finished", Toast.LENGTH_LONG).show()
                score_start.visibility = View.GONE
                score_button_continue.visibility = View.VISIBLE
                score_button_addScore1.isEnabled = false
                score_button_addScore2.isEnabled = false
                score_button_pause.isEnabled = false
            }
        }.start()
    }

    private fun setupUI() {
        val time = intent.getIntExtra(TIME_KEY, 1)
        val team1 = intent.getStringExtra(TEAM1_KEY)
        val team2 = intent.getStringExtra(TEAM2_KEY)

        val seconds = (time * 60) % 60
        score_time.text = String.format("%d:%02d", time, seconds)

        score_textView_team1.text = team1
        score_textView_team2.text = team2
    }

    private fun updateUI(millisUntilFinished: Long) {
        var seconds = (millisUntilFinished / 1000).toInt()
        val minutes = seconds / 60
        seconds %= 60

        score_time.text = String.format("%d:%02d", minutes, seconds)
    }
}