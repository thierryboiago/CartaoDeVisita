package br.com.dio.cartaodevisita

import android.app.Application
import br.com.dio.cartaodevisita.data.AppDatabase
import br.com.dio.cartaodevisita.data.PokemonRepository

class App:Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { PokemonRepository(database.pokemonDao()) }
}