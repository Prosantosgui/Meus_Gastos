package br.edu.infnet.meusgastos.main.ui.chart


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie


class ChartView() : AppCompatActivity(){

    private val TAG = "CHARTVIEW"


    fun configChartView(ChartView: AnyChartView, header: List<String>, values: MutableList<Float?>) {
    val graph : Pie = AnyChart.pie()

    val dataPieChart: MutableList<DataEntry> = mutableListOf()

    Log.i(TAG, values.toString())

    for (i in 0..13){
        dataPieChart.add(ValueDataEntry(header.elementAt(i), values[i]))
    }

    graph.data(dataPieChart)
    graph.title("Gastos")
    ChartView.setChart(graph)

}


}