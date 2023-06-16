package br.edu.infnet.meusgastos.login.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import br.edu.infnet.meusgastos.repository.DespesasRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginViewModel() : ViewModel(){

    val TAG = "ViewModel"
    val repository = DespesasRepository.get()

    // Auth:

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return DespesasRepository.auth.signInWithEmailAndPassword(email, password)
    }

    fun signOn(
        email: String,
        password: String
    ): Task<AuthResult> {
        return repository.cadastrarUsuarioComSenha(
            email,
            password
        )
    }
}