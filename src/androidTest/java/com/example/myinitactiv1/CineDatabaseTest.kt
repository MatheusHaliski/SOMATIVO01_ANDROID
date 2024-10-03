package com.example.myinitactiv1

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CineDatabaseTest {

    private lateinit var db: CineDatabase // Classe de banco de dados
    private lateinit var cinemaFilmeDao: CinemaFilmeDao // DAO

    @Before
    fun setUp() {
        // Cria um banco de dados em memória para testes
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CineDatabase::class.java // Nome da sua classe de banco de dados
        ).build()

        // Inicializa o DAO
        cinemaFilmeDao = db.cinemaFilmeDao() // Nome correto do seu DAO
    }

    @After
    fun tearDown() {
        // Limpa o banco de dados após cada teste
        db.close()
    }

    @Test
    fun testInsertAndRetrieveMovie() = runBlocking {
        // Cria um novo filme
        val movie = Filme(1, "Inception", 2)

        // Insere o filme na mock database
        cinemaFilmeDao.insertFilme(movie)

        // Busca o filme pelo ID
        val retrievedMovie = cinemaFilmeDao.getMovieById(1)

        // Verifica se o filme foi inserido corretamente
        assertNotNull(retrievedMovie)
        assertEquals(movie.id, retrievedMovie.id)
        assertEquals(movie.cinemaId, retrievedMovie.cinemaId)
    }
    @Test
    fun testInsertAndRetrieveMovie2() = runBlocking {
        // Cria um novo filme
        val movie1 = Filme(1, "Inception", 2)
        val movie2 = Filme(2, "DeadPool:2", 2)
        // Insere o filme na mock database
        cinemaFilmeDao.insertFilme(movie1)
        cinemaFilmeDao.insertFilme(movie2)
        // Busca o filme pelo ID
        val retrievedMovie1 = cinemaFilmeDao.getMovieById(1)
        val retrievedMovie2 = cinemaFilmeDao.getMovieById(2)
        // Verifica se o filme foi inserido corretamente
        assertNotNull(retrievedMovie2)
        assertEquals(retrievedMovie2.id, retrievedMovie1.id)
        assertEquals(retrievedMovie1.cinemaId, retrievedMovie2.cinemaId)
    }
}
