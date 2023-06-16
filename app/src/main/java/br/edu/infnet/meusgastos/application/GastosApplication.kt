package br.edu.infnet.meusgastos.application

import android.app.Application
import br.edu.infnet.meusgastos.repository.DespesasRepository

class GastosApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        DespesasRepository.initialize()
    }
}