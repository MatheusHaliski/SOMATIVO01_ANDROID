package com.example.myinitactiv1;
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


@Database(entities = [FilmeHorario::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun timeDao():HorarioDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ) .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
        suspend fun populateTime(dao: HorarioDao) {

            dao.delAllHorarios()

            // Lista para cinemaid = 1
            val cinema1Times = listOf(
                FilmeHorario(nome = "Os fantasmas ainda se divertem:Bettlejuice", horario = "29/10 - 18:35PM", idioma = "ING(LEGENDADO)", cinemaid = 1),
                FilmeHorario(nome = "É assim que acaba", horario = "28/10 - 19:35PM", idioma = "ING(LEGENDADO)", cinemaid = 1),
                FilmeHorario(nome = "Transformers: O início", horario = "23/10 - 16:35PM", idioma = "ING(LEGENDADO)", cinemaid = 1),
                FilmeHorario(nome = "Deadpool & Wolverine", horario = "24/10 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 1),
                FilmeHorario(nome = "Meu Malvado Favorito 4", horario = "24/10 - 19:35PM", idioma = "ING(LEGENDADO)", cinemaid = 1)
            )

            // Lista para cinemaid = 2
            val cinema2Times = listOf(
                FilmeHorario(nome = "Os fantasmas ainda se divertem:Bettlejuice", horario = "24/10 - 14:35PM", idioma = "ING(LEGENDADO)", cinemaid = 2),
                FilmeHorario(nome = "Passagrana", horario = "24/10 - 09:35PM", idioma = "ING(LEGENDADO)", cinemaid = 2),
                FilmeHorario(nome = "Transformers: O início", horario = "24/10 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 2),
                FilmeHorario(nome = "Deadpool & Wolverine", horario = "24/10 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 2),
                FilmeHorario(nome = "Meu Malvado Favorito 4", horario = "24/10 - 19:35PM", idioma = "ING(LEGENDADO)", cinemaid = 2)
            )

            // Lista para cinemaid = 3
            val cinema3Times = listOf(
                FilmeHorario(nome = "Os fantasmas ainda se divertem:Bettlejuice", horario = "24/09 - 14:35PM", idioma = "ING(LEGENDADO)", cinemaid = 3),
                FilmeHorario(nome = "É assim que acaba", horario = "24/09 - 09:35PM", idioma = "ING(LEGENDADO)", cinemaid = 3),
                FilmeHorario(nome = "Transformers: O início", horario = "24/09 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 3),
                FilmeHorario(nome = "Deadpool & Wolverine", horario = "24/09 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 3),
                FilmeHorario(nome = "Meu Malvado Favorito 4", horario = "24/09 - 19:35PM", idioma = "ING(LEGENDADO)", cinemaid = 3)
            )

            // Lista para cinemaid = 4
            val cinema4Times = listOf(
                FilmeHorario(nome = "Os fantasmas ainda se divertem:Bettlejuice", horario = "24/09 - 14:35PM", idioma = "ING(LEGENDADO)", cinemaid = 4),
                FilmeHorario(nome = "É assim que acaba", horario = "24/09 - 09:35PM", idioma = "ING(LEGENDADO)", cinemaid = 4),
                FilmeHorario(nome = "Transformers: O início", horario = "24/09 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 4),
                FilmeHorario(nome = "Deadpool & Wolverine", horario = "24/09 - 11:35PM", idioma = "ING(LEGENDADO)", cinemaid = 4),
                FilmeHorario(nome = "Meu Malvado Favorito 4", horario = "24/09 - 19:35PM", idioma = "ING(LEGENDADO)", cinemaid = 4)
            )


            // Inserir todas as listas de uma vez para cada cinema
            dao.insertAllTime(cinema1Times)
            dao.insertAllTime(cinema2Times)
            dao.insertAllTime(cinema3Times)
            dao.insertAllTime(cinema4Times)
        }

    }
}
