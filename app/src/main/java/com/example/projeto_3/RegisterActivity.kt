package com.example.projeto_3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_3.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnRegistrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString()
            val aceitou = binding.checkboxTermos.isChecked

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, getString(R.string.erro_campos_vazios), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEmailValido(email)) {
                Toast.makeText(this, getString(R.string.erro_email_invalido), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha.length < 6) {
                Toast.makeText(this, getString(R.string.erro_senha_curta), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!aceitou) {
                Toast.makeText(this, getString(R.string.erro_termos), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inseriu = dbHelper.insertUser(email, senha)
            if (inseriu) {
                Toast.makeText(this, getString(R.string.sucesso_registro), Toast.LENGTH_SHORT).show()
                finish() // volta ao Login
            } else {
                Toast.makeText(this, getString(R.string.erro_registro_existente), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValido(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}
