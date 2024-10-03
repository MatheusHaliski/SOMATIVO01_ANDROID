package com.example.myinitactiv1

import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FourthActivity : AppCompatActivity() {

    private lateinit var appDatabase: AppDatabase // Banco de dados da aplicação
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: ListAdapter // Adaptador para a lista de regionais

    // Instancia o ViewModel usando o Factory
    private val viewModel: FourthViewModel by viewModels {
        FourthViewModelFactory(AppDatabase.getDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        appDatabase = AppDatabase.getDatabase(this)

        // Inicializa o RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRegionais)

        // Configura o GridLayoutManager com 3 colunas
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val cinemaId = intent.getIntExtra("cinemaId", -1)
        val nome = intent.getStringExtra("filmenome")

        // Verificar se nome é "TODOS" e carregar dados adequadamente
        if (nome == "TODOS") {
            viewModel.getFullHorarios(cinemaId).observe(this, Observer { regs ->
                if (regs != null && regs.isNotEmpty()) {
                    listAdapter = ListAdapter(regs)
                    recyclerView.adapter = listAdapter
                } else {
                    Toast.makeText(this, "Nenhum dado encontrado!", Toast.LENGTH_SHORT).show()
                }
            })
        } else if (cinemaId != -1 && nome != null) {
            viewModel.getHorariosByCinemaAndName(cinemaId, nome).observe(this, Observer { regs ->
                if (regs != null && regs.isNotEmpty()) {
                    listAdapter = ListAdapter(regs)
                    recyclerView.adapter = listAdapter
                } else {
                    Toast.makeText(this, "Nenhum dado encontrado!", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Dados inválidos", Toast.LENGTH_SHORT).show()
        }
// Popula o banco de dados (se necessário)
        populateData()
    }
    private fun populateData() {
        // Obter uma instância do banco de dados
        val db = AppDatabase.getDatabase(this)

        // Usar coroutines para rodar a função populateTime em uma thread separada
        CoroutineScope(Dispatchers.IO).launch {
            db.timeDao().let { dao ->
                AppDatabase.populateTime(dao,this@FourthActivity)
            }
        }
    }
}
