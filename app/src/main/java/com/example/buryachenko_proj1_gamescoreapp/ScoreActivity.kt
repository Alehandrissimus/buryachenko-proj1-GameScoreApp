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

        scoreButtonCancel.setOnClickListener {
            timer?.cancel()
            this.finish()
        }
        scoreButtonStart.setOnClickListener {
            scoreButtonAddScore1.isEnabled = true
            scoreButtonAddScore2.isEnabled = true
            scoreButtonStart.isEnabled = false
            scoreButtonPause.isEnabled = true
            setupTimer()
        }

        var team1Score = 0
        var team2Score = 0
        scoreButtonAddScore1.setOnClickListener {
            team1Score += 1
            scoreTextViewTeam1Score.text =
                resources.getString(R.string.team_score_template, team1Score)
        }
        scoreButtonAddScore2.setOnClickListener {
            team2Score += 1
            scoreTextViewTeam2Score.text =
                resources.getString(R.string.team_score_template, team2Score)
        }

        scoreButtonPause.setOnClickListener {
            if (!timerIsPaused) {
                timerIsPaused = true
                scoreButtonPause.isEnabled = false
                scoreButtonAddScore1.isEnabled = false
                scoreButtonAddScore2.isEnabled = false
                scoreButtonPause.text = getString(R.string.continuee)
            } else {
                timerIsPaused = false
                scoreButtonAddScore1.isEnabled = true
                scoreButtonAddScore2.isEnabled = true
                setupTimer()
                scoreButtonPause.text = getString(R.string.pause)
            }
        }

        scoreButtonContinue.setOnClickListener {
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
                    scoreButtonPause.isEnabled = true
                    cancel()
                }
            }

            override fun onFinish() {
                Toast.makeText(this@ScoreActivity, "Finished", Toast.LENGTH_LONG).show()
                scoreButtonStart.visibility = View.GONE
                scoreButtonContinue.visibility = View.VISIBLE
                scoreButtonAddScore1.isEnabled = false
                scoreButtonAddScore2.isEnabled = false
                scoreButtonPause.isEnabled = false
            }
        }.start()
    }

    private fun setupUI() {
        val time = intent.getIntExtra(TIME_KEY, 1)
        val team1 = intent.getStringExtra(TEAM1_KEY)
        val team2 = intent.getStringExtra(TEAM2_KEY)

        val seconds = (time * 60) % 60
        scoreTimeTextView.text = String.format("%d:%02d", time, seconds)

        scoreTextViewTeam1.text = team1
        scoreTextViewTeam2.text = team2
    }

    private fun updateUI(millisUntilFinished: Long) {
        var seconds = (millisUntilFinished / 1000).toInt()
        val minutes = seconds / 60
        seconds %= 60

        scoreTimeTextView.text = String.format("%d:%02d", minutes, seconds)
    }
}