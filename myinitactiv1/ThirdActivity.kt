package com.example.myinitactiv1

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {

    private val apiKey = "c225c1db6cc42e9ecadd0e8f563ff88b" // Sua chave da API TMDb
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private var movieList: List<Movie> = emptyList() // Lista de filmes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        recyclerView = findViewById(R.id.recycler_view_users)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fazer a chamada para buscar filmes usando Retrofit
        RetrofitClient.movieApiService.getNowPlayingMovies(apiKey).enqueue(object :
            Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    // Sucesso ao buscar filmes
                    val movies = response.body()?.results ?: emptyList()
                    movieList = movies // Atualiza a lista de filmes

                    // Cria o adapter com a lista de filmes e define o comportamento do onClick
                    movieAdapter = MovieAdapter(movieList) { movie ->
                        // Ação ao clicar em um item de filme
                        val intent = Intent(this@ThirdActivity, SixthActivity::class.java)
                        intent.putExtra("MOVIE_ID", movie.id)
                        startActivity(intent)
                    }

                    // Configura o adapter no RecyclerView
                    recyclerView.adapter = movieAdapter
                    recyclerView.addItemDecoration(BorderItemDecoration()) // Adiciona decoração

                } else {
                    Log.e("com.example.myinitactiv1.ThirdActivity", "Erro na resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Falha ao buscar filmes
                Log.e("com.example.myinitactiv1.ThirdActivity", "Erro ao buscar filmes: ${t.message}")
                Toast.makeText(this@ThirdActivity, "Erro ao buscar filmes", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Classe para a decoração das bordas
    inner class BorderItemDecoration : RecyclerView.ItemDecoration() {
        private val paint = Paint().apply {
            color = android.graphics.Color.BLACK
            strokeWidth = 2f // Espessura da borda
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(c, parent, state)

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                // Desenhar bordas
                c.drawLine(child.left.toFloat(), child.top.toFloat(), child.right.toFloat(), child.top.toFloat(), paint) // Top
                c.drawLine(child.left.toFloat(), child.bottom.toFloat(), child.right.toFloat(), child.bottom.toFloat(), paint) // Bottom
                c.drawLine(child.left.toFloat(), child.top.toFloat(), child.left.toFloat(), child.bottom.toFloat(), paint) // Left
                c.drawLine(child.right.toFloat(), child.top.toFloat(), child.right.toFloat(), child.bottom.toFloat(), paint) // Right
            }
        }
    }
}
