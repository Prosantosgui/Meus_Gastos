package br.edu.infnet.meusgastos.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.models.Categoria
import br.edu.infnet.meusgastos.models.CategoriaTotal
import br.edu.infnet.meusgastos.models.Despesa
import br.edu.infnet.meusgastos.models.DespesaComId
import br.edu.infnet.meusgastos.repository.DespesasRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject
import java.util.*

//TODO Verificar a transição pro dataStore e a questão do context necessário pra declar[ar ele
//TODO realizar o total para cada categoria
class MainViewModel : ViewModel() {

    val TAG = "ViewModel"
    val repository = DespesasRepository.get()
    //val dataStore = DataStoreManager()

    private val _totalDespesas = MutableLiveData<Float>(0.0F)
    val totalDespesas : LiveData<Float> = _totalDespesas
    fun setTotalDespesas(value: Float){
        _totalDespesas.postValue(value)
    }

    //TODO: Fun novas aqui

    fun setTotalCategoria(totalCategoria: MutableLiveData<Float> ,value: Float){
        totalCategoria.postValue(value)
    }

    private val _totalComida = MutableLiveData<Float>(0.0F)
    val valorTotalComida : LiveData<Float> = _totalComida

    private val _totalTransporte = MutableLiveData<Float>(0.0F)
    val valorTotalTransporte : LiveData<Float> = _totalTransporte

    private val _totalContas = MutableLiveData<Float>(0.0F)
    val valorTotalContas : LiveData<Float> = _totalContas

    private val _totalLazer = MutableLiveData<Float>(0.0F)
    val valorTotalLazer : LiveData<Float> = _totalLazer

    private val _totalCompras = MutableLiveData<Float>(0.0F)
    val valorTotalCompras : LiveData<Float> = _totalCompras

    private val _totalMercado = MutableLiveData<Float>(0.0F)
    val valorTotalMercado : LiveData<Float> = _totalMercado

    private val _totalCartao = MutableLiveData<Float>(0.0F)
    val valorTotalCartao : LiveData<Float> = _totalCartao

    private val _totalEducacao = MutableLiveData<Float>(0.0F)
    val valorTotalEducacao : LiveData<Float> = _totalEducacao

    private val _totalPets = MutableLiveData<Float>(0.0F)
    val valorTotalPets : LiveData<Float> = _totalPets

    private val _totalPresente = MutableLiveData<Float>(0.0F)
    val valorTotalPresente : LiveData<Float> = _totalPresente

    private val _totalRoupas = MutableLiveData<Float>(0.0F)
    val valorTotalRoupas : LiveData<Float> = _totalRoupas

    private val _totalSaude = MutableLiveData<Float>(0.0F)
    val valorTotalSaude : LiveData<Float> = _totalSaude

    private val _totalViagem = MutableLiveData<Float>(0.0F)
    val valorTotalViagem : LiveData<Float> = _totalViagem

    private val _totalOutros = MutableLiveData<Float>(0.0F)
    val valorTotalOutros : LiveData<Float> = _totalOutros

    val _valoresChart = mutableListOf(valorTotalComida.value,valorTotalTransporte.value, valorTotalContas.value,valorTotalLazer.value,valorTotalCompras.value,valorTotalMercado.value,valorTotalCartao.value,valorTotalEducacao.value,valorTotalPets.value,valorTotalPresente.value,valorTotalRoupas.value,valorTotalSaude.value,valorTotalViagem.value,valorTotalOutros.value)


    var valoresChart = MutableLiveData<MutableList<Float?>>()
    fun populaValoresChart(lista: MutableList<Float?>){
        if (lista.isEmpty() == false){
            valoresChart.postValue(lista)
        }else{
            Log.i("populaValoresChart","valoresChart lista vazia")
        }

    }

    fun calculaValorTotalCategoria(lista: List<DespesaComId>){

        var valorComida = 0.0F
        var valorTransporte = 0.0F
        var valorContas = 0.0F
        var valorLazer = 0.0F
        var valorCompras = 0.0F
        var valorMercado = 0.0F
        var valorCartao = 0.0F
        var valorEducacao = 0.0F
        var valorPets = 0.0F
        var valorPresente = 0.0F
        var valorRoupas = 0.0F
        var valorSaude = 0.0F
        var valorViagem = 0.0F
        var valorOutros = 0.0F

        lista.forEach { despesa ->
            when (despesa.categoriaNome) {
                "Comida" -> valorComida += despesa.valor
                "Transporte" -> valorTransporte += despesa.valor
                "Contas" -> valorContas += despesa.valor
                "Lazer" -> valorLazer += despesa.valor
                "Compras" -> valorCompras += despesa.valor
                "Mercado" -> valorMercado += despesa.valor
                "Cartão" -> valorCartao += despesa.valor
                "Educação" -> valorEducacao += despesa.valor
                "Pets" -> valorPets += despesa.valor
                "Presente" -> valorPresente += despesa.valor
                "Roupas" -> valorRoupas += despesa.valor
                "Saúde" -> valorSaude += despesa.valor
                "Viagem" -> valorViagem += despesa.valor
                "Outros" -> valorOutros += despesa.valor
            }
            //Testar o setTotalCategoria aqui e na proxima }

        }

        setTotalCategoria(_totalComida, valorComida)
        setTotalCategoria(_totalTransporte, valorTransporte)
        setTotalCategoria(_totalContas, valorContas)
        setTotalCategoria(_totalLazer, valorLazer)
        setTotalCategoria(_totalCompras, valorCompras)
        setTotalCategoria(_totalMercado, valorMercado)
        setTotalCategoria(_totalCartao, valorCartao)
        setTotalCategoria(_totalEducacao, valorEducacao)
        setTotalCategoria(_totalPets, valorPets)
        setTotalCategoria(_totalPresente, valorPresente)
        setTotalCategoria(_totalRoupas, valorRoupas)
        setTotalCategoria(_totalSaude, valorSaude)
        setTotalCategoria(_totalViagem, valorViagem)
        setTotalCategoria(_totalOutros, valorOutros)

        //TODO retirar logs depois 20/05
        Log.i("ValorTotalContas:", valorTotalContas.toString())
        Log.i("ValorTotalPresente:", valorTotalPresente.toString())
    }

    fun totalPorNomeCategoria(nomeCategoria: String): Float {
        when (nomeCategoria) {
            "Comida" -> return valorTotalComida.value!!
            "Transporte" -> return valorTotalTransporte.value!!
            "Contas" -> return valorTotalContas.value!!
            "Lazer" -> return valorTotalLazer.value!!
            "Compras" -> return valorTotalCompras.value!!
            "Mercado" -> return valorTotalMercado.value!!
            "Cartão" -> return valorTotalCartao.value!!
            "Educação" -> return valorTotalEducacao.value!!
            "Pets" -> return valorTotalPets.value!!
            "Presente" -> return valorTotalPresente.value!!
            "Roupas" -> return valorTotalRoupas.value!!
            "Saúde" -> return valorTotalSaude.value!!
            "Viagem" -> return valorTotalViagem.value!!
            "Outros" -> return valorTotalOutros.value!!
        }
        return 0.0F
    }

    fun getCurrentUserEmail(): String {
        return repository.getCurrentUser()?.email ?: "Email não encontrado"
    }

    fun logout() {
        repository.logout()
    }

    fun atualizaUsuario(){
        repository.atualizaUsuarioAtual()
    }

    fun criarDespesa(despesa: Despesa): Task<DocumentReference>{
        return repository.criarDespesa(despesa)
    }

    fun deleteDespesa(id: String){
        repository.deleteDespesa(id)
    }

    /////Ouvir documentos em uma coleção do firebase/////

    fun observerColecaoDespesas(){

        repository.getDespesasColecao()
            .addSnapshotListener{ snapshots, e ->
                if(e != null){
                    Log.w(TAG, "listen:error ",e)
                    return@addSnapshotListener
                }

                val listaInput = mutableListOf<DespesaComId>()

                val listaRemocao = mutableListOf<String>()

                val listaModificacao = mutableListOf<DespesaComId>()

                for (dc in snapshots!!.documentChanges){
                    when(dc.type){

                        DocumentChange.Type.ADDED -> {

                            val despesa = dc.document.toObject<Despesa>()
                            val id = dc.document.id
                            val despesaComId = despesaToDespesaComId(despesa, id)

                            Log.i(TAG,"DespesaComId: ${despesaComId}")
                            listaInput.add(despesaComId)

                        }

                        DocumentChange.Type.MODIFIED -> {
                            val despesa = dc.document.toObject<Despesa>()
                            val id = dc.document.id
                            val despesaComId = despesaToDespesaComId(despesa, id)

                            Log.i(TAG,"Modificação - DespesaComId: ${despesaComId}")
                            listaModificacao.add(despesaComId)
                        }

                        DocumentChange.Type.REMOVED -> {
                            val id = dc.document.id

                            Log.i(TAG, "Id removido: ${id}")
                            listaRemocao.add(dc.document.id)
                        }

                    }

                }

                addListaToDespesasComId(listaInput)
                removeFromDespesasComId(listaRemocao)
                modifyInDespesasComId(listaModificacao)


            }


    }

    private fun modifyInDespesasComId(listaModificacao: List<DespesaComId>) {
        Log.i(TAG, "listaModificacao: ${listaModificacao}")
        if (listaModificacao.isNotEmpty()){
            for(itemModificado in listaModificacao){
                modifyItemInListaDespesasComId(itemModificado)
            }
        }
    }

    fun modifyItemInListaDespesasComId(itemModificado: DespesaComId) {
        val listaAntiga = despesasComId.value
        val listaNova = mutableListOf<DespesaComId>()

        listaAntiga?.forEach { itemDaLista ->
            if (itemModificado.id == itemDaLista.id) {
                listaNova.add(itemModificado)
            } else {
                listaNova.add(itemDaLista)
            }
        }
        setDespesaComId(listaNova)
    }

    private fun removeFromDespesasComId(listaRemocao: MutableList<String>) {

        val listaAntiga = despesasComId.value

        val listaNova = mutableListOf<DespesaComId>()

        Log.i(TAG, "listaRemocao: ${listaRemocao}")

        if (listaRemocao.isNotEmpty()) {
            listaAntiga?.forEach {
                Log.i(TAG, "item da lista Antiga: ${it.id}")
                if (it.id in listaRemocao) {
                    Log.i(TAG, "item ${it.id} está dentro da listaRemocao")

                    //listaNova.add(it)
                } else {
                    Log.i(TAG, "item ${it.id} _NÃO_ está dentro da listaRemocao")

                    listaNova.add(it)
                }
            }
            setDespesaComId(listaNova)
        }


    }

    private fun addListaToDespesasComId(listaInput: MutableList<DespesaComId>) {

        val listaAntiga = despesasComId.value

        val listaNova = mutableListOf<DespesaComId>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setDespesaComId(listaNova)

    }

    fun despesaToDespesaComId(despesa: Despesa, id: String): DespesaComId {
        return DespesaComId(
            nome = despesa.nome,
            valor = despesa.valor,
            data = despesa.data,
            descricao = despesa.descricao,
            categoriaNome = despesa.categoriaNome,
            id = id
        )
    }

    private val _selectedDespesaComId = MutableLiveData<DespesaComId>()
    val selectedDespesaComId: LiveData<DespesaComId> = _selectedDespesaComId
    fun setSelectedDespesaComId(value: DespesaComId) {
        _selectedDespesaComId.postValue(value)

    }

    fun atualizaDespesa(despesa: Despesa) {
        repository.atualizaDespesa(selectedDespesaComId.value?.id, despesa)
    }

    fun deletarDespesa(){
        selectedDespesaComId.value?.let { repository.deleteDespesa(it.id) }
    }


    private val _despesasComId = MutableLiveData<List<DespesaComId>>()
    val despesasComId : LiveData<List<DespesaComId>> = _despesasComId

    fun setDespesaComId(value : List<DespesaComId>){
        _despesasComId.postValue(value)
        setTotalDespesas(calculaValorTotal(value))
        calculaValorTotalCategoria(value)

    }


    fun calculaValorTotal(lista: List<DespesaComId>): Float{

        var valorfinal = 0.0F
        lista.forEach { despesa ->
            valorfinal += despesa.valor
            Log.i(TAG, valorfinal.toString())
        }
        return valorfinal

    }

    /////////////////////////////////////////////////////

    ////////Categorias//////////
     val categoriaLista = mutableListOf<Categoria>(
        Categoria(1, "Comida", R.drawable.cat_comida),
        Categoria(2, "Transporte", R.drawable.cat_transporte),
        Categoria(3, "Contas", R.drawable.cat_contas),
        Categoria(4, "Lazer", R.drawable.cat_lazer),
        Categoria(5, "Compras", R.drawable.cat_compras),
        Categoria(6, "Mercado", R.drawable.cat_mercado),
        Categoria(7, "Cartão", R.drawable.cat_cartao),
        Categoria(8, "Educação", R.drawable.cat_educacao),
        Categoria(9, "Pets", R.drawable.cat_pets),
        Categoria(10, "Presente", R.drawable.cat_presente),
        Categoria(11, "Roupas", R.drawable.cat_roupas),
        Categoria(12, "Saúde", R.drawable.cat_saude),
        Categoria(13, "Viagem", R.drawable.cat_viagem),
        Categoria(14, "Outros", R.drawable.cat_outros),

    )
    val categorias = MutableLiveData<List<Categoria>>()


    fun getListaPorCategoria(string: String): List<DespesaComId>{
        return despesasComId.value?.filter {
            it.categoriaNome.contains(string.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            })
        }?: emptyList()
    }
    
    fun getListaTotalPorCategoria(categorias: List<Categoria>): List<CategoriaTotal>{

        val lista = mutableListOf<CategoriaTotal>()

        categorias.forEach { categoria ->
            val totalPercent = totalPorNomeCategoria(categoria.nome) * 100 / totalDespesas.value!!
            val totalCategoria = CategoriaTotal(categoria.id ,categoria.nome, categoria.imagem, totalPorNomeCategoria(categoria.nome), totalPercent)

            lista.add(totalCategoria)
        }

        Log.i("ListaTotal", lista.toString())
        return lista
    }
    fun getListaTotalPorCategoriaFloat(categorias: MutableList<String>): MutableList<Float>{

        val lista = mutableListOf<Float>()

        categorias.forEach { categoria ->
            val valorCategoria = totalPorNomeCategoria(categoria)
//            val totalPercent = totalPorNomeCategoria(categoria.nome) * 100 / totalDespesas.value!!
//            val totalCategoria = CategoriaTotal(categoria.id ,categoria.nome, categoria.imagem, totalPorNomeCategoria(categoria.nome), totalPercent )

            lista.add(valorCategoria)
        }

        Log.i("ListaTotal", lista.toString())
        return lista
    }



    init {
        observerColecaoDespesas()
        categorias.value = categoriaLista
        populaValoresChart(_valoresChart)

    }

}