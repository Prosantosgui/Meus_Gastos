package br.edu.infnet.meusgastos.main.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.databinding.FragmentCriarDespesaBinding
import br.edu.infnet.meusgastos.main.ui.adapters.CategoriasAdapter
import br.edu.infnet.meusgastos.main.ui.adapters.CategoriasListener
import br.edu.infnet.meusgastos.models.Categoria
import br.edu.infnet.meusgastos.models.Despesa
import br.edu.infnet.meusgastos.utils.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.log


class CriarDespesasFragment : Fragment() {

    val TAG = "CriarDespesas"

    val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentCriarDespesaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCriarDespesaBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.categorias.observe(viewLifecycleOwner){
            atualizaRecyclerView(it)
        }
    }

    private fun atualizaRecyclerView(lista: List<Categoria>?) {
        adapter.submitList(lista)
        binding.rvCategorias.adapter = adapter
    }

    val adapter = CategoriasAdapter(
        object : CategoriasListener {
            override fun onImgCategoriaClick(categoria: Categoria) {
                Log.i(TAG,categoria.nome)
                //    Log.i(TAG,categoria.imagem.toString())

                binding.inputCategoriaDespesaNome.setText(categoria.nome)
            }
        }
    )

    private fun setupRecyclerView() {
        binding.rvCategorias.adapter = adapter
        binding.rvCategorias.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setupClickListeners() {
        binding.btnCriarDespesa.setOnClickListener {
            onCriarClick()

        }
        binding.inputDataDespesa.setOnClickListener {
            onDatePickerClick()
        }

    }

    private fun onCriarClick() {
        val despesa = getDespesaFromInputs()

        viewModel.criarDespesa(despesa)
            .addOnSuccessListener { documentReference ->
                toast("Criado com sucesso com id: ${documentReference.id}")
                navUp()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, e.toString())
                toast("Falha ao criar Despesa")
            }
    }


    //Alteração da data: Date(Despesa.model) para LocalDate devido a essa fun
    //Testar se o create funciona assim



    //Criar a formatação do input na view!!!
    @SuppressLint("NewApi")
    private fun getDespesaFromInputs(): Despesa {

       // val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        // 22/04/2002
        binding.apply {
//            when(inputDataDespesa.length()){
//                2 -> "${inputDataDespesa}/"
//                5 -> "${inputDataDespesa}/"
//                11 -> inputDataDespesa.text.toString().substring(0,10)
//            }
            //Log.e("Nome: ", inputNomeDespesa.text.toString())
            //Log.e("Valor: ", inputValorDespesa.text.toString())
            //Log.e("Descricao: ", inputDescricaoDespesa.text.toString())
            //Log.e("Data Despesa(input): ", inputDataDespesa.text.toString())
            //val data = LocalDate.parse(inputDataDespesa.text.toString(), formatter)
            //val data = LocalDate.parse(inputDataDespesa.toString(), formatter)

            Log.e("Data Despesa: ", inputDataDespesa.toString())

            return Despesa(
                nome = getTextInput(inputNomeDespesa) ,
                valor = getFloatInput(inputValorDespesa),
                data = getTextInput(inputDataDespesa),
                descricao =  getTextInput(inputDescricaoDespesa),
                categoriaNome = getTextInput(inputCategoriaDespesaNome)
            )
        }
    }

//    private fun getIdFromCategoria(categoria: String): Int {
//        return when(categoria){
//            "res/drawable/cat_comida.xml" -> R.drawable.cat_comida
//            "res/drawable/cat_transporte.xml" -> R.drawable.cat_transporte
//
//            else -> 0
//        }
//    }


    private fun setupViews() {
        activity?.setTitle("Criar Despesa")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun onDatePickerClick() {
        val cal = Calendar.getInstance()

        // Interface passada para dentro do DatePickerDialog que recupera a data selecionada:
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Comando que passa a data par ao layout:
                setInitialDate(cal)
            }
        callDatePicker(dateSetListener)
    }

    fun callDatePicker(dateSetListener: DatePickerDialog.OnDateSetListener?) {
        val cal = Calendar.getInstance()
        val datePicker =
            DatePickerDialog(
                requireContext(),
                //android.R.style.Theme_Holo_Light_Dialog,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
        datePicker.show()
    }

    // Comando que passa a data par ao layout:
    fun setInitialDate(cal: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = java.text.SimpleDateFormat(myFormat)
        binding.inputDataDespesa.setText("${sdf.format(cal.time)}")
    }

}