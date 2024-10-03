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

@RunWith(AndroidJUnit4::class) // Indica que o teste deve ser executado no ambiente Android
class UserDaoTest {

    private lateinit var db: UserDatabase // Substitua por sua classe de banco de dados
    private lateinit var userDao: UserDao // Substitua pelo seu DAO

    @Before
    fun setup() {
        // Cria um banco de dados em memória para testes
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java // Substitua pelo nome da sua classe de banco de dados
        ).build()

        // Inicializa o DAO
        userDao = db.userDao() // Substitua pelo nome correto do seu DAO
    }

    @After
    fun teardown() {
        // Fecha o banco de dados após os testes
        db.close()
    }

    @Test
    fun testUserEmailExists() = runBlocking {
        // Cria um usuário e insere no banco de dados
        val user = User(
            id = 1,
            name = "Matheus",
            password = "password123",
            birthdate = "1990-01-01",
            email = "matheus@example.com"
        )
        userDao.insertUser(user) // Suponha que este seja o método de inserção

        // Consulta o banco de dados para verificar se o e-mail existe
        val userFromDb = userDao.getUserByEmail("matheus@example.com")

        // Verifica se o usuário foi encontrado
        assertNotNull(userFromDb)
        assertEquals("matheus@example.com", userFromDb?.email)
    }

    @Test
    fun testUserEmailDoesNotExist() = runBlocking {
        // Cria um usuário e insere no banco de dados
        val user = User(
            id = 1,
            name = "Matheus",
            password = "password123",
            birthdate = "1990-01-01",
            email = "matheus@example.com"
        )
        userDao.insertUser(user) // Suponha que este seja o método de inserção
        // Consulta o banco de dados para um e-mail inexistente
        val userFromDb = userDao.getUserByEmail("matheus@example.com")

        // Verifica se nenhum usuário foi retornado
        assertNull(userFromDb)
    }
}
