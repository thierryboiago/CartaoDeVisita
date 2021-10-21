package br.com.dio.cartaodevisita.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.cartaodevisita.data.Pokemon


import android.R
import br.com.dio.cartaodevisita.databinding.ItemCartaoPokemonBinding
import com.squareup.picasso.Picasso
import java.util.*


class PokemonAdapter :
    ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(DiffCallback()) {
    var listenerShare: (View) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartaoPokemonBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCartaoPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon) {
            binding.tvNome.text = item.nome.capitalize()
            binding.tvTipo1.text = item.tipo1.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            binding.tvTipo2.text = item.tipo2.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            binding.tvId.text = "#"+item.id.toString()
            //imgProdutoAberto = findViewById(R.id.imgProdutoAberto);

            Picasso.get()
                .load( item.urlImagem)
                .into(binding.imgPokemon);



            binding.cdContent.setCardBackgroundColor(item.cor)
            binding.cdContent.setOnClickListener {
                listenerShare(it)
            }
        }
    }
}


class DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) =
        oldItem.id == newItem.id
}