package com.example.buryachenko_proj1_gamescoreapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Score (
    val time: Int,
    val team1: String,
    val team1Score: Int,
    val team2: String,
    val team2Score: Int
    ) : Parcelable