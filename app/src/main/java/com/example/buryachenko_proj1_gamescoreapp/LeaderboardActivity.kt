package com.example.buryachenko_proj1_gamescoreapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_leaderboards.*

class LeaderboardActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, LeaderboardActivity::class.java)
            context.startActivity(intent)
        }

        fun startWithFlags(context: Context) {
            val intent = Intent(context, LeaderboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboards)

        setupRecycleView()
        setupListeners()
    }

    override fun onBackPressed() {
        MainActivity.start(this)
        super.onBackPressed()
    }

    private fun setupRecycleView() {
        leaderboardRecycleContainer.adapter = LeaderboardRecycleAdapter({
            removingItem(it)
        }, ScoreData.list)

        leaderboardRecycleContainer.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

        leaderboardRecycleContainer.addItemDecoration(
            DividerItemDecoration(applicationContext, RecyclerView.VERTICAL)
        )
    }

    private fun removingItem(position: Int) {
        ScoreData.list.removeAt(position)
        leaderboardRecycleContainer.adapter?.notifyDataSetChanged()
    }

    private fun setupListeners() {
        var isSorted = false
        leaderboardsButtonSort.setOnClickListener {
            if (!isSorted) {
                ScoreData.list.sortBy { it.team1Score + it.team2Score }
                isSorted = true
            } else {
                ScoreData.list.sortByDescending { it.team1Score + it.team2Score }
                isSorted = false
            }
            leaderboardRecycleContainer.adapter?.notifyDataSetChanged()
        }
    }

}