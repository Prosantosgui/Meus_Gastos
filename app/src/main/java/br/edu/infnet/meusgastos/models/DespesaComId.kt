package br.edu.infnet.meusgastos.models

data class DespesaComId (
    val nome: String = "",
    val valor: Float = 0F,
    val data: String = "",
    val descricao: String = "",
    val categoriaNome: String = "",
    var id: String = ""

    )