package com.example.myinitactiv1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(entities = [FilmeHorario::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun timeDao(): HorarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateTime(dao: HorarioDao, context: Context) {
            dao.delAllHorarios()

            val cinema1Times = createFilmeHorarioList(1)
            val cinema2Times = createFilmeHorarioList(2)
            val cinema3Times = createFilmeHorarioList(3)
            val cinema4Times = createFilmeHorarioList(4)

            updatePosterPaths(cinema1Times, dao, context)
            updatePosterPaths(cinema2Times, dao, context)
            updatePosterPaths(cinema3Times, dao, context)
            updatePosterPaths(cinema4Times, dao, context)

            // Inserir todas as listas de uma vez para cada cinema
            dao.insertAllTime(cinema1Times)
            dao.insertAllTime(cinema2Times)
            dao.insertAllTime(cinema3Times)
            dao.insertAllTime(cinema4Times)
        }

        private fun createFilmeHorarioList(cinemaId: Int): List<FilmeHorario> {
            return listOf(
                FilmeHorario(nome = "Bettlejuice Bettlejuice", horario = "29/10 - 18:35PM", idioma = "ING(LEGENDADO)", posterPath = "", cinemaid = cinemaId),
                FilmeHorario(nome = "It Ends with Us", horario = "28/10 - 19:35PM", idioma = "ING(LEGENDADO)", posterPath = "", cinemaid = cinemaId),
                FilmeHorario(nome = "Transformers One", horario = "23/10 - 16:35PM", idioma = "ING(LEGENDADO)", posterPath = "", cinemaid = cinemaId),
                FilmeHorario(nome = "The Substance", horario = "24/10 - 11:35PM", idioma = "ING(LEGENDADO)", posterPath = "", cinemaid = cinemaId),
                FilmeHorario(nome = "Hounds of War", horario = "24/10 - 19:35PM", idioma = "ING(LEGENDADO)", posterPath = "", cinemaid = cinemaId)
            )
        }

        private suspend fun updatePosterPaths(filmes: List<FilmeHorario>, dao: HorarioDao, context: Context) {
            filmes.forEach { filme ->
                val posterPath = fetchPosterPath(filme.nome, context)
                if (posterPath != null) {
                    filme.posterPath = posterPath // Atualiza o posterPath no objeto FilmeHorario
                    dao.updateTime(filme) // Atualiza no banco de dados
                }
            }
        }

        private fun fetchPosterPath(nomeFilme: String, context: Context): String? {
            // Converte o nome do filme para o formato do arquivo drawable
            val formattedName = nomeFilme.lowercase().replace(" ", "_")
            val resourceId = context.resources.getIdentifier(formattedName, "drawable", context.packageName)

            // Verifica se o recurso existe
            return if (resourceId != 0) {
                "android.resource://${context.packageName}/$resourceId" // Retorna o URI do recurso drawable
            } else {
                null
            }
        }
    }
}
