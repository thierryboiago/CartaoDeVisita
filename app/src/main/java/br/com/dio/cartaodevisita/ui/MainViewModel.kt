package br.com.dio.cartaodevisita.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.dio.cartaodevisita.data.Pokemon
import br.com.dio.cartaodevisita.data.PokemonRepository
import org.json.JSONObject
import java.net.URL

class MainViewModel(private val pokemonRepository: PokemonRepository): ViewModel() {

    fun insert(pokemon: Pokemon) {
        pokemonRepository.insert(pokemon)
    }

    fun getAll(): LiveData<List<Pokemon>> {
        return pokemonRepository.getAll()
    }

    fun getAllPokemonList(): LiveData<List<Pokemon>> {
        pokemonRepository.populateList()
        return pokemonRepository.listaPokemon
    }

}

class MainViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}