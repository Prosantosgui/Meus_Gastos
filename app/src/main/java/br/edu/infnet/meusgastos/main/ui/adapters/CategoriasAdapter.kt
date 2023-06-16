package br.edu.infnet.meusgastos.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.meusgastos.databinding.CategoriaListItemBinding
import br.edu.infnet.meusgastos.databinding.DespesaListItemBinding
import br.edu.infnet.meusgastos.models.Categoria


class CategoriasAdapter(val listener: CategoriasListener):
    ListAdapter<
            Categoria,
            CategoriasAdapter.ViewHolder
            >(CategoriasDiffCallback()) {

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
        val binding: CategoriaListItemBinding,
        val listener: CategoriasListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Categoria, position: Int) {
            binding.apply {

                tvNomeCategoria.text = item.nome
                imvCategoria.setImageResource(item.imagem)

                imvCategoria.setOnClickListener{
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
            fun from(parent: ViewGroup, listener: CategoriasListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoriaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}
class CategoriasDiffCallback : DiffUtil.ItemCallback<Categoria>() {

    override fun areItemsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
        return oldItem == newItem
    }
}

interface CategoriasListener{
    fun onImgCategoriaClick(categoria: Categoria)
}