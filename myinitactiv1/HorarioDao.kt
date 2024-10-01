package com.example.myinitactiv1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HorarioDao {


    @Query("SELECT * FROM FilmeHorario WHERE cinemaId = :cinemaId AND nome= :nome")
    suspend fun getAllHorarios(cinemaId: Int,nome: String): List<FilmeHorario>
    @Query("DELETE FROM FilmeHorario")
    suspend fun delAllHorarios()
    @Insert
    suspend fun insertAllTime(horario: List<FilmeHorario>)
    @Query("SELECT * FROM FilmeHorario WHERE cinemaId = :cinemaId"  )
    suspend fun getFullTableHorarios(cinemaId: Int): List<FilmeHorario>
}