package com.example.buryachenko_proj1_gamescoreapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_element.view.*

class LeaderboardRecycleAdapter(
    private val listener: (position: Int) -> Unit,
    private val items: List<Score>
) : RecyclerView.Adapter<LeaderboardRecycleAdapter.LeaderBoardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_element, parent, false)

        return LeaderBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setupListeners(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class LeaderBoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(score: Score) {
            val score1 = "Score: ${score.team1Score}"
            val score2 = "Score: ${score.team2Score}"
            val winner = "Winner"
            val loser = "Loser"
            val tie = "Tie"
            itemView.recycle_textView_team1.text = score.team1
            itemView.recycle_textView_team2.text = score.team2
            itemView.recycle_textView_team1Score.text = score1
            itemView.recycle_textView_team2Score.text = score2
            when {
                score.team1Score > score.team2Score -> {
                    itemView.recycle_textView_team1Tag.text = winner
                    itemView.recycle_textView_team1Tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                    itemView.recycle_textView_team2Tag.text = loser
                    itemView.recycle_textView_team2Tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                }
                score.team1Score < score.team2Score -> {
                    itemView.recycle_textView_team1Tag.text = winner
                    itemView.recycle_textView_team1Tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    itemView.recycle_textView_team2Tag.text = loser
                    itemView.recycle_textView_team2Tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                }
                score.team1Score == score.team2Score -> {
                    itemView.recycle_textView_team1Tag.text = tie
                    itemView.recycle_textView_team1Tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.stock_text))
                    itemView.recycle_textView_team2Tag.text = tie
                    itemView.recycle_textView_team2Tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.stock_text))
                }
            }
        }

        fun setupListeners(position: Int) {
            itemView.recycle_button.setOnClickListener {
                listener.invoke(position)
            }
        }
    }
}