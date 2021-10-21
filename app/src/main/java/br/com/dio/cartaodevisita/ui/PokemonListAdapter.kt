package br.com.dio.cartaodevisita.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.cartaodevisita.data.Pokemon
import br.com.dio.cartaodevisita.databinding.ItemCartaoPokemonBinding
import br.com.dio.cartaodevisita.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso
import java.util.*

class PokemonListAdapter :
    ListAdapter<Pokemon, PokemonListAdapter.ViewHolder>(DiffCallback()) {
    var listenerShare: (View) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon) {
            binding.tvNomePokemon.text = item.nome.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }




            binding.cdContentPokemon.setOnClickListener {
                listenerShare(it)
            }
        }
    }

}

