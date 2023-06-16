package br.edu.infnet.meusgastos.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.meusgastos.HomeActivity
import br.edu.infnet.meusgastos.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){

    val viewModel by viewModels<LoginViewModel>()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em atividades
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#activities
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onStart() {
        super.onStart()
        if(viewModel.isLoggedIn()){
            startHomeActivity()
        }
    }


     fun startHomeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}