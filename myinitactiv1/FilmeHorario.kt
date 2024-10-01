package com.example.myinitactiv1;
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FilmeHorario")
data class FilmeHorario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val horario: String,
    val idioma: String,
    val cinemaid: Int=0
)