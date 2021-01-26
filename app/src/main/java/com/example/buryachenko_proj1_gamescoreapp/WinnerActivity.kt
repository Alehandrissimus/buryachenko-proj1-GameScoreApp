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
        winnerTextViewTeam1.text = data.team1
        winnerTextViewTeam2.text = data.team2
        winnerTextViewTeam1Score.text =
            resources.getString(R.string.team_score_template, data.team1Score)
        winnerTextViewTeam2Score.text =
            resources.getString(R.string.team_score_template, data.team2Score)
        when {
            data.team1Score > data.team2Score -> {
                winnerTextTeam1Tag.text = getString(R.string.winner)
                winnerTextTeam1Tag.setTextColor(ContextCompat.getColor(this, R.color.green))
                winnerTextViewTeam2Tag.text = getString(R.string.loser)
                winnerTextViewTeam2Tag.setTextColor(ContextCompat.getColor(this, R.color.red))
            }
            data.team1Score < data.team2Score -> {
                winnerTextTeam1Tag.text = getString(R.string.loser)
                winnerTextTeam1Tag.setTextColor(ContextCompat.getColor(this, R.color.red))
                winnerTextViewTeam2Tag.text = getString(R.string.winner)
                winnerTextViewTeam2Tag.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
            else -> {
                winnerTextTeam1Tag.text = getString(R.string.tie)
                winnerTextViewTeam2Tag.text = getString(R.string.tie)
            }
        }
    }

    private fun setupListeners(data: Score) {
        winnerButtonCancel.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        winnerButtonShare.setOnClickListener {
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

        winnerButtonContinue.setOnClickListener {
            LeaderboardActivity.start(this, data)
            finish()
        }
    }
}