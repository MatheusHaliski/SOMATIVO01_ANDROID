package com.example.myinitactiv1;
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filme")
data class Filme(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val cinemaId: Int  // Relaciona o filme ao cinema
)
