package com.example.projeto_3

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_3.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var dbHelper: DBHelper
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // Recebe o email do usuário logado (enviado pelo LoginActivity)
        userEmail = intent.getStringExtra("USER_EMAIL")

        // Mostra o e-mail na tela
        val printEmail = userEmail?.let { ": $it" } ?: ""
        binding.tvUsuario.text = getString(R.string.usuario_desconhecido, printEmail)

        // Clique para deletar conta
        binding.btnDeletarConta.setOnClickListener {
            userEmail?.let { email ->
                val dialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.confirmar_deletar_titulo))
                    .setMessage(getString(R.string.confirmar_deletar_mensagem))
                    .setPositiveButton(getString(R.string.sim)) { _: DialogInterface, _: Int ->
                        val deleted = dbHelper.deleteUserByEmail(email)
                        if (deleted > 0) {
                            Toast.makeText(
                                this,
                                getString(R.string.sucesso_deletar),
                                Toast.LENGTH_SHORT
                            ).show()
                            // volta para a tela de login
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.erro_deletar),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton(getString(R.string.nao), null)
                    .create()
                dialog.show()
            }
        }
    }
}
