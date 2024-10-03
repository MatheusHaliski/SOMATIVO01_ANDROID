package com.example.myinitactiv1

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterpath: String ,
    val posterPath: Any
)
