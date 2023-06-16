package br.edu.infnet.meusgastos.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.databinding.FragmentSignInBinding
import br.edu.infnet.meusgastos.main.ui.MainActivity
import br.edu.infnet.meusgastos.utils.getTextInput
import br.edu.infnet.meusgastos.utils.nav
import br.edu.infnet.meusgastos.utils.toast
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception


//SaveData funcionando e salvando o Email corretamente ln 115-120
//TODO: Salvar o email e a senha juntos e tambem o Lembrar de mim;
// Após isso preencher os campos com email, senha e também deixar marcada a caixa Lembrar de mim quando o usuário estiver nessa tela
// e tiver as credenciais salvas

class SignInFragment : Fragment() {

    val TAG = "SignIn"
    val viewModel by activityViewModels<LoginViewModel>()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em fragmentos
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments

    private var _binding: FragmentSignInBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val Context.myDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // setup ///////////////////////////////////////////////////////////////////////////////////////

    private fun setup() {
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {

            btnSignIn.setOnClickListener {
                onSignInClick()
            }

            btnSignOn.setOnClickListener {
                onSignOnClick()
            }

        }

    }


    private fun onSignOnClick() {
        nav(R.id.action_signInFragment_to_signOnFragment)
    }

    private fun onSignInClick() {
        val email = getTextInput(binding.inputEmail)
        val password = getTextInput(binding.inputPassword)

        try {
            signIn(email, password)
        }catch(e: Exception){
            toast("Email ou Senha Inválidos!")
            Log.e(TAG, e.toString())
        }



    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private suspend fun saveData(key: String, value: String){
        val prefsKey = stringPreferencesKey(key)
        context?.myDataStore?.edit { settings ->
            settings[prefsKey] = value
        }
    }

    private suspend fun readData(key: String): String?{
        val prefsKey = stringPreferencesKey(key)
        val prefs = context?.myDataStore?.data?.first()
        return prefs?.get(prefsKey)
    }

    private fun signIn(email: String, password: String){

        viewModel.login(email, password)
            .addOnSuccessListener {
                toast("Logado com Sucesso")
                if (binding.storeCheckBox.isChecked){
                    lifecycleScope.launch {
                        val emailKey = "emailKey"
                        val emailValue = binding.inputEmail.text.toString()

                        val passwordKey = "passwordKey"
                        val passwordValue = binding.inputPassword.text.toString()

                        val rememberKey = "rememberKey"
                        val rememberValue = "True"

                        saveData(emailKey,emailValue)
                        saveData(passwordKey, passwordValue)
                        saveData(rememberKey, rememberValue)
                    }
                }
                startMainActivity()
            }
            .addOnFailureListener {
                toast("Falha ao Autenticar\n${it.message}")
            }
    }

    fun startMainActivity(){
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }
}