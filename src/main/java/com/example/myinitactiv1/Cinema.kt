package com.example.myinitactiv1;
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinema")
data class Cinema(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val nome: String
)
