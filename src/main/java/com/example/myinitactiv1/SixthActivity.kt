package com.example.myinitactiv1

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SixthActivity : AppCompatActivity() {

    private val apiKey = "c225c1db6cc42e9ecadd0e8f563ff88b" // Sua API Key do TMDb
    private lateinit var movieTitleTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var moviePosterImageView: ImageView
    private var movieId: Int = 0 // ID do filme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth)

        // Inicializa os views
        movieTitleTextView = findViewById(R.id.movieTitleTextView)
        movieDescriptionTextView = findViewById(R.id.movieDescriptionTextView)
        moviePosterImageView = findViewById(R.id.moviePosterImageView)

        // Obtém o ID do filme da Intent
        movieId = intent.getIntExtra("MOVIE_ID", 0)

        // Fazer a chamada para buscar detalhes do filme
        if (movieId != 0) {
            getMovieDetails(movieId)
        }
    }

    private fun getMovieDetails(movieId: Int) {
        RetrofitClient.movieApiService.getMovieDetails(movieId, apiKey).enqueue(object :
            Callback<MovieDetailsResponse> {
            override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {
                if (response.isSuccessful) {
                    val movieDetails = response.body()
                    movieTitleTextView.text = movieDetails?.title ?: "Título não disponível"
                    movieDescriptionTextView.text = movieDetails?.overview ?: "Descrição não disponível"
                    // Use uma biblioteca como Glide ou Picasso para carregar a imagem do pôster
                    // Use Picasso para carregar a imagem do pôster
                    if (movieDetails != null) {
                        Picasso.get() // Aqui usamos Picasso com o método 'get' ao invés de 'with'
                            .load("https://image.tmdb.org/t/p/w500${movieDetails.posterpath}")
                            .into(moviePosterImageView)
                    }
                } else {
                    Log.e("SixthActivity", "Erro na resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                Log.e("SixthActivity", "Erro ao buscar detalhes do filme: ${t.message}")
                Toast.makeText(this@SixthActivity, "Erro ao buscar detalhes do filme", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
