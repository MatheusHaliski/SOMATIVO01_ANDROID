package com.example.myinitactiv1;
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CinemaFilmeDao {

    // Métodos para Cinema
    @Query("SELECT * FROM cinema")
    suspend fun getAllCinemas(): List<Cinema>
    @Query("SELECT * FROM filme")
    suspend fun getAllFilmes(): List<Filme>

    @Insert
    suspend fun insertCinema(cinema: Cinema)

    @Insert
    suspend fun insertAllCinemas(cinemas: List<Cinema>)

    @Query("DELETE FROM cinema")
     fun deleteAllCinemas()

    // Métodos para Filme
    @Query("SELECT * FROM filme WHERE cinemaId = :cinemaId")
    suspend fun getFilmesByCinema(cinemaId: Int): List<Filme>

    @Insert
    suspend fun insertFilme(filme: Filme)

    @Insert
    suspend fun insertAllFilmes(filmes: List<Filme>)

    @Query("DELETE FROM filme")
     fun deleteAllFilmes()
}

