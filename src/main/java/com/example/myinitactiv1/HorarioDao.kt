package com.example.myinitactiv1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HorarioDao {

    // Consulta todos os horários para um cinema específico e filme específico
    @Query("SELECT * FROM FilmeHorario WHERE cinemaId = :cinemaId AND nome = :nome")
    suspend fun getAllHorarios(cinemaId: Int, nome: String): List<FilmeHorario>

    // Deleta todos os registros da tabela FilmeHorario
    @Query("DELETE FROM FilmeHorario")
    suspend fun delAllHorarios()

    // Insere uma lista de horários na tabela
    @Insert
    suspend fun insertAllTime(horarios: List<FilmeHorario>)

    // Consulta todos os horários para um cinema específico
    @Query("SELECT * FROM FilmeHorario WHERE cinemaId = :cinemaId")
    suspend fun getFullTableHorarios(cinemaId: Int): List<FilmeHorario>

    // Atualiza o horário de um filme na tabela
    @Update
    suspend fun updateTime(horario: FilmeHorario)
}
