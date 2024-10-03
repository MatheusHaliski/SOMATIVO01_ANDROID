package com.example.myinitactiv1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FifthActivity : AppCompatActivity() {

    private lateinit var cinemaSpinner: Spinner
    private lateinit var filmeSpinner: Spinner
    private lateinit var btnFiltrar: Button
    private lateinit var database: CineDatabase
    private lateinit var btnGoToThirdActivity : Button
    private var cinemas: List<Cinema> = listOf()
    private var filmes: List<Filme> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)

        cinemaSpinner = findViewById(R.id.spinner_cinem)
        filmeSpinner = findViewById(R.id.spinner_film)
        btnFiltrar = findViewById(R.id.btn_filtra)

        database = CineDatabase.getDatabase(this)

        lifecycleScope.launch {

            populateDatabase()

            loadCinemas()
        }

        // Ação para quando um cinema for selecionado
        cinemaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCinema = cinemas.getOrNull(position)
                selectedCinema?.let {
                    loadFilmes(it.id)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        btnGoToThirdActivity = findViewById(R.id.btn_go_to_third_activity)
        btnGoToThirdActivity.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        btnFiltrar.setOnClickListener {
            val selectedCinemaPosition = cinemaSpinner.selectedItemPosition
            val selectedCinema = cinemas.getOrNull(selectedCinemaPosition)


            val selectedFilmePosition = filmeSpinner.selectedItemPosition
            val selectedFilme = filmes.getOrNull(selectedFilmePosition)


            if (selectedFilme != null) {
                Toast.makeText(
                    this,
                    "Cinema: ${selectedCinema?.nome} Filme: ${selectedFilme?.titulo}",
                    Toast.LENGTH_LONG
                ).show()

                // Envia o cinemaId e filmenome para a com.example.myinitactiv1.FourthActivity
                val intent = Intent(this, FourthActivity::class.java).apply {
                    putExtra("cinemaId", selectedCinema?.id)
                    putExtra("filmenome", selectedFilme?.titulo)
                }
                startActivity(intent)
            } else if (selectedCinema == null) {
                Toast.makeText(this, "Nenhum cinema selecionado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nenhum filme disponível", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private suspend fun populateDatabase() {
        withContext(Dispatchers.IO) {
            CineDatabase.populateDatabase(database.cinemaFilmeDao())
        }
    }

    private fun loadCinemas() {
        lifecycleScope.launch(Dispatchers.IO) {
            cinemas = database.cinemaFilmeDao().getAllCinemas()

            withContext(Dispatchers.Main) {

                val cinemaNames = cinemas.map { it.nome }
                val adapter = ArrayAdapter(this@FifthActivity, android.R.layout.simple_spinner_item, cinemaNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                cinemaSpinner.adapter = adapter
            }
        }
    }

    private fun loadFilmes(cinemaId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            filmes = database.cinemaFilmeDao().getFilmesByCinema(cinemaId)

            withContext(Dispatchers.Main) {
                if (filmes.isNotEmpty()) {

                    val filmeTitles = filmes.map { it.titulo }
                    val adapter = ArrayAdapter(this@FifthActivity, android.R.layout.simple_spinner_item, filmeTitles)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    filmeSpinner.adapter = adapter
                } else {

                    val adapter = ArrayAdapter(this@FifthActivity, android.R.layout.simple_spinner_item, emptyArray<String>())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    filmeSpinner.adapter = adapter
                    Toast.makeText(this@FifthActivity, "Nenhum filme encontrado para o cinema selecionado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
