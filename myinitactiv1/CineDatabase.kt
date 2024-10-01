package com.example.myinitactiv1;
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myinitactiv1.Cinema
import com.example.myinitactiv1.Filme
import com.example.myinitactiv1.CinemaFilmeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cinema::class, Filme::class], version = 3) // Atualize a versão do banco de dados
abstract class CineDatabase : RoomDatabase() {

    abstract fun cinemaFilmeDao(): CinemaFilmeDao

    companion object {
        @Volatile
        private var INSTANCE: CineDatabase? = null

        fun getDatabase(context: Context): CineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CineDatabase::class.java,
                    "cine_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(dao: CinemaFilmeDao) {
            // Limpar as tabelas
            dao.deleteAllCinemas()
            dao.deleteAllFilmes()

            // Inserir cinemas
            val cinema1 = Cinema(id = 1, nome = "Park Shopping Barigui")
            val cinema2 = Cinema(id = 2, nome = "IMAX Palladium")
            val cinema3 = Cinema(id = 3, nome = "Cinemark Curitiba")
            val cinema4 = Cinema(id = 4, nome = "Cineplex Novo Batel")
            dao.insertCinema(cinema1)
            dao.insertCinema(cinema2)
            dao.insertCinema(cinema3)
            dao.insertCinema(cinema4)

            // Criar a lista de filmes
            val filmes = listOf(
                Filme( titulo = "Os fantasmas ainda se divertem:Bettlejuice", cinemaId = 2),
                Filme( titulo = "Passagrana", cinemaId = 2),
                Filme(titulo = "Deadpool & Wolverine", cinemaId = 2),
                Filme(titulo = "Passagrana",cinemaId = 2),
                Filme(titulo = "Transformers: O início",cinemaId = 2),
                Filme( titulo = "Meu Malvado Favorito 4",cinemaId = 2),
                Filme( titulo = "Golpe de Sorte em Paris",cinemaId = 2),
                Filme(titulo="TODOS",cinemaId = 2)
            )
            val filmes1 = listOf(
                Filme( titulo = "Os fantasmas ainda se divertem:Bettlejuice", cinemaId = 1),
                Filme( titulo = "É assim que acaba", cinemaId = 1),
                Filme(titulo = "Deadpool & Wolverine", cinemaId = 1),
                Filme(titulo = "Passagrana", cinemaId = 1),
                Filme(titulo = "Transformers: O início", cinemaId = 1),
                Filme( titulo = "Meu Malvado Favorito 4", cinemaId = 1),
                Filme( titulo = "Golpe de Sorte em Paris", cinemaId = 1),
                Filme(titulo="TODOS", cinemaId = 1)
            )
            val filmes3 = listOf(
                Filme( titulo = "Os fantasmas ainda se divertem:Bettlejuice", cinemaId = 3),
                Filme( titulo = "É assim que acaba", cinemaId = 3),
                Filme(titulo = "Deadpool & Wolverine", cinemaId = 3),
                Filme(titulo = "Passagrana", cinemaId = 3),
                Filme(titulo = "Transformers: O início", cinemaId = 3),
                Filme( titulo = "Meu Malvado Favorito 4", cinemaId = 3),
                Filme( titulo = "Golpe de Sorte em Paris", cinemaId = 3),
                Filme(titulo="TODOS", cinemaId = 3)
            )
            val filmes4 = listOf(
                Filme( titulo = "Os fantasmas ainda se divertem:Bettlejuice", cinemaId = 4),
                Filme( titulo = "É assim que acaba", cinemaId = 4),
                Filme(titulo = "Deadpool & Wolverine", cinemaId = 4),
                Filme(titulo = "Passagrana", cinemaId = 4),
                Filme(titulo = "Transformers: O início", cinemaId = 4),
                Filme( titulo = "Meu Malvado Favorito 4", cinemaId = 4),
                Filme( titulo = "Golpe de Sorte em Paris", cinemaId = 4),
                Filme(titulo="TODOS", cinemaId = 4)
            )

            // Inserir todos os filmes em um único bloco
            dao.insertAllFilmes(filmes)
            dao.insertAllFilmes(filmes1)
            dao.insertAllFilmes(filmes3)
            dao.insertAllFilmes(filmes4)
        }
    }

}


