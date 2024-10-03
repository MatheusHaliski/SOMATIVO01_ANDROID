package com.example.myinitactiv1

import androidx.room.PrimaryKey

data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val releaseDate: String
)