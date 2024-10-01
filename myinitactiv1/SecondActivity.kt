package com.example.myinitactiv1

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    @Inject
    lateinit var userDatabase: UserDatabase // Injetando a dependência

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Referências aos campos do layout
        val nameField = findViewById<EditText>(R.id.cadastro_nome)
        val passwordField = findViewById<EditText>(R.id.cadastro_senha)
        val confirmPasswordField = findViewById<EditText>(R.id.confirmacao_senha)
        val emailField = findViewById<EditText>(R.id.cadastro_email)
        val dateField = findViewById<EditText>(R.id.et_date)

        val registerButton = findViewById<Button>(R.id.botao_confirma_cadastro)

        // Configurando o DatePickerDialog ao clicar no campo de data
        dateField.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val birthdate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    dateField.setText(birthdate)
                }, year, month, day)

            datePickerDialog.show()
        }

        // Ação do botão de cadastro
        registerButton.setOnClickListener {
            val name = nameField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()
            val email = emailField.text.toString()
            val birthdate = dateField.text.toString()

            // Validação simples de senha
            if (password == confirmPassword) {
                // Salvando no banco de dados Room
                lifecycleScope.launch {
                    val user = User(name = name, password = password, birthdate = birthdate, email = email)
                    userDatabase.userDao().insertUser(user)

                    // Exibindo Toast com os dados do usuário
                    val message = "Usuário: $name\nEmail: $email\nNascimento: $birthdate"
                    Toast.makeText(this@SecondActivity, "Cadastro concluído: $message", Toast.LENGTH_LONG).show()

                    // Retornando para a MainActivity com os dados do usuário
                    val intent = Intent(this@SecondActivity, MainActivity::class.java)
                    intent.putExtra("user_name", name)
                    intent.putExtra("user_email", email)
                    intent.putExtra("user_birthdate", birthdate)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
