package br.edu.infnet.meusgastos.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.meusgastos.databinding.FragmentResumoBinding
import br.edu.infnet.meusgastos.main.ui.adapters.TotalCategoriasAdapter
import br.edu.infnet.meusgastos.main.ui.adapters.TotalCategoriasListener
import br.edu.infnet.meusgastos.models.Categoria
import br.edu.infnet.meusgastos.models.CategoriaTotal


class ResumoFragment : Fragment() {

    private val TAG = "RESUMO"

    val viewModel: MainViewModel by activityViewModels()

    private  var _binding: FragmentResumoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResumoBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.i(TAG, "ResumoFragment iniciado!")

        setup()
        return view
    }

    private fun setup() {

        setupViews()
        setupObservers()
        setupRecyclerView()
    }

    val adapter = TotalCategoriasAdapter(
        object : TotalCategoriasListener{
            override fun onImgCategoriaClick(categoria: CategoriaTotal) {
                Log.i(TAG, categoria.nome)
            }
        }
    )

    private fun setupRecyclerView() {
        binding.RvTotalCategorias.adapter = adapter
        binding.RvTotalCategorias.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservers() {
        viewModel.categorias.observe(viewLifecycleOwner){
            atualizaRecyclerView(it)
        }
    }

    private fun atualizaRecyclerView(categorias: List<Categoria>?) {
        adapter.submitList(categorias?.let { viewModel.getListaTotalPorCategoria(it) })
        binding.RvTotalCategorias.adapter = adapter
    }

    private fun setupViews() {
        activity?.setTitle("Resumo")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}