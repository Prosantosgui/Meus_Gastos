package br.edu.infnet.meusgastos.main.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.meusgastos.databinding.FragmentEditarDespesaBinding
import br.edu.infnet.meusgastos.main.ui.adapters.CategoriasAdapter
import br.edu.infnet.meusgastos.main.ui.adapters.CategoriasListener
import br.edu.infnet.meusgastos.models.Categoria
import br.edu.infnet.meusgastos.models.Despesa
import br.edu.infnet.meusgastos.utils.getFloatInput
import br.edu.infnet.meusgastos.utils.getTextInput
import br.edu.infnet.meusgastos.utils.navUp
import br.edu.infnet.meusgastos.utils.toast
import java.util.*


class EditarDespesaFragment : Fragment() {

    val TAG = "EditarDespesa"

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditarDespesaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarDespesaBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupObservers()
        setupClickListeners()
        setupRecyclerView()
    }

    private fun setupClickListeners() {
        binding.apply{
            btnAtualizarDespesa.setOnClickListener {
                onAtualizarClick()
            }
            btnDeletarDespesa.setOnClickListener {
                onDeletarClick()
            }

            binding.inputDataDespesa.setOnClickListener {
                onDatePickerClick()
            }

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

    

    private fun onAtualizarClick() {
        val despesa = getdespesaFromInputs()
        viewModel.atualizaDespesa(despesa)
        Log.i(TAG, viewModel.selectedDespesaComId.toString())
        navUp()
    }


    private fun onDeletarClick() {
        ConfirmDeleteDialogClick()
    }

    @SuppressLint("NewApi")
    private fun getdespesaFromInputs(): Despesa {
    //    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    //    val formatter2 =  DateTimeFormatter.ofPattern("yyyy-MM-dd")
        binding.apply {
            Log.i(TAG, inputDataDespesa.text.toString())
      //      val data = LocalDate.parse(inputDataDespesa.text.toString(), formatter)

            return Despesa(
                nome = getTextInput(inputNomeDespesa),
                valor = getFloatInput(inputValorDespesa),
                data = getTextInput(inputDataDespesa),
                descricao =  getTextInput(inputDescricaoDespesa),
                categoriaNome = getTextInput(inputCategoriaDespesaNome)
            )
        }
    }

    private fun setupObservers() {
        viewModel.categorias.observe(viewLifecycleOwner){
            atualizaRecyclerView(it)
        }

        viewModel.selectedDespesaComId.observe(viewLifecycleOwner){
            binding.apply {
                inputNomeDespesa.setText(it.nome)
                inputValorDespesa.setText(it.valor.toString())
                inputDataDespesa.text = it.data
                inputDescricaoDespesa.setText(it.descricao)
                inputCategoriaDespesaNome.setText(it.categoriaNome)
            }
        }
    }

    private fun setupViews() {
        activity?.setTitle("Editar")
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

    //Dialog de confirmação
    // Constrói e implementa os eventos de cliques de um diálogo com dois botões
    fun ConfirmDeleteDialogClick() {
        val dialog = TwoChoicesDialogFragment(
            object : TwoChoicesAlertDialogFragmentListener {
                override fun onPositiveButtonClick() {
                    viewModel.deletarDespesa()
                    toast("Deletado com sucesso!")
                    navUp()

                }

                override fun onNegativeButtonClick() {
                    toast("Operação cancelada!")

                }
            }
        )
        dialog.show(childFragmentManager, "TwoChoicesAlert")
    }

}