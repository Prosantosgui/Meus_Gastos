package br.edu.infnet.meusgastos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import br.edu.infnet.meusgastos.databinding.FragmentChartBinding
import br.edu.infnet.meusgastos.main.ui.MainViewModel
import br.edu.infnet.meusgastos.models.Categoria
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie

class ChartFragment : Fragment() {

    private val TAG = "CHART"

    private val viewModel: MainViewModel by activityViewModels()

    private  var _binding: FragmentChartBinding? = null

    private val binding get() = _binding!!

    private var valores = mutableListOf<Float>()
    private var header = mutableListOf("Comida","Transporte","Contas","Lazer","Compras","Mercado", "Cartão", "Educação", "Pets", "Presente", "Roupas", "Saúde", "Viagem", "Outros")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.i(TAG, "ChartFragment iniciado!")
        setupViews()
        setupObservers()
//        values = viewModel.valoresChart.value!!
        return view
    }

    private fun setupObservers() {
        viewModel.totalDespesas.observe(viewLifecycleOwner){
            popularValue(header)
        }
    }

    private fun setupViews() {
        activity?.setTitle("Chart")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun configChartView(ChartView: AnyChartView) {
        val graph: Pie = AnyChart.pie()

        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        for (i in 0..13) {
            dataPieChart.add(ValueDataEntry(header.elementAt(i), valores[i]))
        }

        graph.data(dataPieChart)
        graph.title("Gastos")
        ChartView.setChart(graph)
    }
    private fun popularValue(categorias: MutableList<String>) {
        valores = viewModel.getListaTotalPorCategoriaFloat(categorias)
        //viewModel.getListaTotalPorCategoriaFloat(it)

        Log.i(TAG, valores.toString())
        configChartView(binding.chartGastos)
    }

//    private fun popularValue(categorias: List<Categoria>) {
//        valores = categorias?.let { viewModel.getListaTotalPorCategoriaFloat(it) }!!
//        Log.i(TAG, valores.toString())
//        configChartView(binding.chartGastos)
//    }

}