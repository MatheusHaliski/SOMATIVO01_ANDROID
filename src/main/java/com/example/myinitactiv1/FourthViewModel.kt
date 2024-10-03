package com.example.myinitactiv1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myinitactiv1.AppDatabase.Companion.populateTime
import kotlinx.coroutines.Dispatchers

class FourthViewModel(private val appDatabase: AppDatabase) : ViewModel() {

    // Função para obter todos os horários de um cinema específico
    fun getFullHorarios(cinemaId: Int) = liveData<List<FilmeHorario>>(Dispatchers.IO) {
        val regs = appDatabase.timeDao().getFullTableHorarios(cinemaId)
        emit(regs) // Emite os dados recuperados
    }

    // Função para obter horários com base no cinema e nome do filme
    fun getHorariosByCinemaAndName(cinemaId: Int, nome: String) = liveData<List<FilmeHorario>>(Dispatchers.IO) {
        val regs = appDatabase.timeDao().getAllHorarios(cinemaId, nome)
        emit(regs) // Emite os dados recuperados
    }

}
