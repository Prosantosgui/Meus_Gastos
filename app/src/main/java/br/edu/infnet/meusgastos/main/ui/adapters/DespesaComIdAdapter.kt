package br.edu.infnet.meusgastos.main.ui.adapters

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.databinding.DespesaListItemBinding
import br.edu.infnet.meusgastos.databinding.FragmentDashboardBinding
import br.edu.infnet.meusgastos.models.DespesaComId


class DespesaComIdAdapter(val listener: DespesaComIdListener) :
    ListAdapter<
            DespesaComId,
            DespesaComIdAdapter.ViewHolder
            >(DespesaComIdDiffCallback()) {

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
            val binding: DespesaListItemBinding,
            val listener: DespesaComIdListener
        ) : RecyclerView.ViewHolder(binding.root) {

            fun getIdFromNome(nomeCategoria: String ) : Int {
                return when(nomeCategoria){
                    "Comida" -> R.drawable.cat_comida
                    "Transporte" -> R.drawable.cat_transporte
                    "Contas" -> R.drawable.cat_contas
                    "Lazer" -> R.drawable.cat_lazer
                    "Compras" -> R.drawable.cat_compras
                    "Mercado" -> R.drawable.cat_mercado
                    "Cartão" -> R.drawable.cat_cartao
                    "Educação" -> R.drawable.cat_educacao
                    "Pets" -> R.drawable.cat_pets
                    "Presente" -> R.drawable.cat_presente
                    "Saúde" -> R.drawable.cat_saude
                    "Roupas" -> R.drawable.cat_roupas
                    "Viagem" -> R.drawable.cat_viagem
                    "Outros" -> R.drawable.cat_outros
                    else -> 0
                }
            }

            fun bind(item: DespesaComId, position: Int) {
                binding.apply {

                    tvRvNome.text = item.nome
                    tvRvValor.text = "R$ "+ item.valor.toString()
                    tvRvData.text = item.data
                    RVimvDespesa.setImageResource(getIdFromNome(item.categoriaNome))

                    itemContainer.setOnClickListener {
                        listener.onEditClick(item)
                    }
//                    ivDelete.setOnClickListener {
//                        listener.onDeleteClick(item)
//                    }

                }
            }

            companion object {
                fun from(parent: ViewGroup, listener: DespesaComIdListener): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = DespesaListItemBinding.inflate(
                        layoutInflater, parent, false
                    )
                    return ViewHolder(binding, listener)
                }
            }
        }
    
}
class DespesaComIdDiffCallback : DiffUtil.ItemCallback<DespesaComId>() {

    override fun areItemsTheSame(oldItem: DespesaComId, newItem: DespesaComId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DespesaComId, newItem: DespesaComId): Boolean {
        return oldItem == newItem
    }
}



    interface DespesaComIdListener {
        fun onEditClick(despesa: DespesaComId)
        fun onDeleteClick(despesa: DespesaComId)
    }    