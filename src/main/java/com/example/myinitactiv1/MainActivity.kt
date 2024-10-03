package com.example.myinitactiv1

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide // Importando Glide
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var userDatabase: UserDatabase
    private lateinit var regionalDatabase: AppDatabase
    private lateinit var cineDatabase: CineDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDatabase = UserDatabase.getDatabase(this)
        regionalDatabase = AppDatabase.getDatabase(this)
        cineDatabase = CineDatabase.getDatabase(this)

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountButton = findViewById<Button>(R.id.botao_cadastro)
        val largeTopCircle = findViewById<ImageView>(R.id.largeTopCircle)
        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val imageViewGif = findViewById<ImageView>(R.id.logoImage) // Adicionando referência ao ImageView do GIF

        // Carregar a imagem com transformação de bordas arredondadas
        val radius = 250f
        Picasso.get()
            .load(R.drawable.logo_cinema_02)
            .fit()
            .centerCrop()
            .transform(RoundedTransformation(radius))
            .into(largeTopCircle)

        // Carregar o GIF
        Glide.with(this)
            .asGif()
            .load(R.drawable.sua_animacao) // Substitua pelo nome do seu arquivo GIF
            .into(imageViewGif)

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
                            animateWelcomeText(welcomeText)
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

    private fun showOtherViews() {
        // Tornar todos os elementos visíveis
        findViewById<ImageView>(R.id.largeTopCircle).visibility = View.VISIBLE
        findViewById<EditText>(R.id.username).visibility = View.VISIBLE
        findViewById<EditText>(R.id.password).visibility = View.VISIBLE
        findViewById<Button>(R.id.loginButton).visibility = View.VISIBLE
        findViewById<Button>(R.id.botao_cadastro).visibility = View.VISIBLE
        findViewById<Switch>(R.id.themeSwitch).visibility = View.VISIBLE
        findViewById<TextView>(R.id.registerText).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.logoImageView).visibility = View.VISIBLE
        findViewById<TextView>(R.id.welcomeText).visibility = View.GONE
        findViewById<ImageView>(R.id.logoImage).visibility = View.GONE
    }

    private fun hideOtherViews() {
        // Tornar todos os elementos invisíveis
        findViewById<ImageView>(R.id.largeTopCircle).visibility = View.GONE
        findViewById<EditText>(R.id.username).visibility = View.GONE
        findViewById<EditText>(R.id.password).visibility = View.GONE
        findViewById<ImageView>(R.id.logoImageView).visibility = View.GONE
        findViewById<Button>(R.id.loginButton).visibility = View.GONE
        findViewById<Button>(R.id.botao_cadastro).visibility = View.GONE
        findViewById<Switch>(R.id.themeSwitch).visibility = View.GONE
        findViewById<TextView>(R.id.registerText).visibility = View.GONE
        findViewById<TextView>(R.id.welcomeText).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.logoImage).visibility = View.VISIBLE
    }

    private fun animateWelcomeText(welcomeText: TextView) {
        // Tornar o GIF visível
        findViewById<ImageView>(R.id.logoImage).visibility = View.VISIBLE

        // Ocultar outros elementos
        hideOtherViews()
        welcomeText.visibility = View.VISIBLE // Torna o TextView visível

        // Animação de entrada
        val fadeIn = ObjectAnimator.ofFloat(welcomeText, "alpha", 0f, 1f).setDuration(2800)
        val scaleX = ObjectAnimator.ofFloat(welcomeText, "scaleX", 0.8f, 1f).setDuration(2800)
        val scaleY = ObjectAnimator.ofFloat(welcomeText, "scaleY", 0.8f, 1f).setDuration(2800)

        // Animação de rotação sutil
        val rotate = ObjectAnimator.ofFloat(welcomeText, "rotation", 0f, 5f).setDuration(2400)
        rotate.repeatCount = ObjectAnimator.INFINITE
        rotate.repeatMode = ObjectAnimator.REVERSE

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeIn, scaleX, scaleY)
        animatorSet.start()

        // Redirecionar para a próxima atividade após a animação
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                rotate.cancel() // Cancela a rotação após o término da animação
                val intent = Intent(this@MainActivity, FifthActivity::class.java)
                startActivity(intent)
                finish() // Finaliza a MainActivity
            }
        })
    }}
