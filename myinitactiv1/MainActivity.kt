package com.example.myinitactiv1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.squareup.picasso.Picasso
class MainActivity : AppCompatActivity() {

    private lateinit var userDatabase: UserDatabase
    private lateinit var regionalDatabase: AppDatabase
    private lateinit var cineDatabase: CineDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando o banco de dados Room
        userDatabase = UserDatabase.getDatabase(this)
        regionalDatabase = AppDatabase.getDatabase(this)
        cineDatabase = CineDatabase.getDatabase(this)  // Inicialize cineDatabase aqui

        // Referências aos campos e botões no layout
        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountButton = findViewById<Button>(R.id.botao_cadastro)
        // Referência ao ImageView no layout
        val largeTopCircle = findViewById<ImageView>(R.id.largeTopCircle)

        // Definir o raio desejado para as bordas arredondadas (exemplo: 50f)
        val radius = 250f

// Carregar a imagem com transformação de bordas arredondadas
        Picasso.get()
            .load(R.drawable.logo_cinema_02)
            .fit()
            .centerCrop()
            .transform(RoundedTransformation(radius)) // Aplicar a transformação personalizada
            .into(largeTopCircle)
        // Ação do botão de login
        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val user = userDatabase.userDao().getUserByUsernameAndPassword(username, password)

                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            Toast.makeText(this@MainActivity, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity, FifthActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "Usuário não existe ou senha incorreta", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação do botão de criar conta
        createAccountButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val themeSwitch: Switch = findViewById(R.id.themeSwitch)
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        }
    }

