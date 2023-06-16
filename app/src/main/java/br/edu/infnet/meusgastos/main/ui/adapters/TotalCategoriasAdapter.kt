package br.edu.infnet.meusgastos.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.meusgastos.databinding.TotalCategoriaListItemBinding
import br.edu.infnet.meusgastos.models.CategoriaTotal
import br.edu.infnet.meusgastos.utils.usingKotlinStringFormat


class TotalCategoriasAdapter(val listener: TotalCategoriasListener):
    ListAdapter<
            CategoriaTotal,
            TotalCategoriasAdapter.ViewHolder
            >(TotalCategoriasDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent, listener)
    }



    class ViewHolder private constructor(
        val binding: TotalCategoriaListItemBinding,
        val listener: TotalCategoriasListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoriaTotal, position: Int) {
            val placeValor = "R$" + item.valor.toString()
            val placePorcent = usingKotlinStringFormat(item.percent, 2) + "%"

            binding.apply {

                tvRvTotalNome.text = item.nome
                RVImvTotalCategoria.setImageResource(item.imagem)
                tvRvTotalValor.text = placeValor
                tvRvTotalPorcent.text = placePorcent

                RVImvTotalCategoria.setOnClickListener{
                    listener.onImgCategoriaClick(item)
                }

//                tvRvNome.text = item.nome
//                tvRvValor.text = item.valor.toString()
//                tvRvData.text = item.data


//                    ivEdit.setOnClickListener {
//                        listener.onEditClick(item)
//                    }
//                    ivDelete.setOnClickListener {
//                        listener.onDeleteClick(item)
//                    }

            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: TotalCategoriasListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TotalCategoriaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}
class TotalCategoriasDiffCallback : DiffUtil.ItemCallback<CategoriaTotal>() {

    override fun areItemsTheSame(oldItem: CategoriaTotal, newItem: CategoriaTotal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoriaTotal, newItem: CategoriaTotal): Boolean {
        return oldItem == newItem
    }
}

interface TotalCategoriasListener{
    fun onImgCategoriaClick(categoria: CategoriaTotal)
}