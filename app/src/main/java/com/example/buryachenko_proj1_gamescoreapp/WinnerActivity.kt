package com.example.buryachenko_proj1_gamescoreapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_winner.*

class WinnerActivity : AppCompatActivity() {

    companion object {

        private const val KEY_SCORE = "KEY_SCORE"

        fun start(context: Context, score: Score) {
            val intent = Intent(context, WinnerActivity::class.java).apply {
                putExtra(KEY_SCORE, score)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val data = intent.getParcelableExtra<Score>(KEY_SCORE)
        data?.also {
            setupData(it)
            setupListeners(it)
        }
    }

    private fun setupData(data: Score) {
        winner_textView_team1.text = data.team1
        winner_textView_team2.text = data.team2
        winner_textView_team1Score.text =
            resources.getString(R.string.team_score_template, data.team1Score)
        winner_textView_team2Score.text =
            resources.getString(R.string.team_score_template, data.team2Score)
        when {
            data.team1Score > data.team2Score -> {
                winner_textView_team1Tag.text = getString(R.string.winner)
                winner_textView_team1Tag.setTextColor(ContextCompat.getColor(this, R.color.green))
                winner_textView_team2Tag.text = getString(R.string.loser)
                winner_textView_team2Tag.setTextColor(ContextCompat.getColor(this, R.color.red))
            }
            data.team1Score < data.team2Score -> {
                winner_textView_team1Tag.text = getString(R.string.loser)
                winner_textView_team1Tag.setTextColor(ContextCompat.getColor(this, R.color.red))
                winner_textView_team2Tag.text = getString(R.string.winner)
                winner_textView_team2Tag.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
            else -> {
                winner_textView_team1Tag.text = getString(R.string.tie)
                winner_textView_team2Tag.text = getString(R.string.tie)
            }
        }
    }

    private fun setupListeners(data: Score) {
        winner_button_cancel.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        winner_button_share.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "Subject line")
                when {
                    data.team1Score > data.team2Score -> {
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "\"${data.team1}\" wins \"${data.team2}\" in the recent match with score ${data.team1Score}:${data.team2Score}!"
                        )
                    }
                    data.team1Score < data.team2Score -> {
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "\"${data.team2}\" wins \"${data.team1}\" in the recent match with score ${data.team2Score}:${data.team1Score}!"
                        )
                    }
                    else -> {
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "\"${data.team1}\" and \"${data.team2}\" were tied in the recent match with score ${data.team1Score}:${data.team2Score}!"
                        )
                    }
                }
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Share using"))
        }

        winner_button_continue.setOnClickListener {
            LeaderboardActivity.start(this, data)
            finish()
        }
    }
}