package br.com.dio.cartaodevisita.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL

class PokemonRepository(private val dao: PokemonDAO) {

    fun insert(pokemon: Pokemon) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(pokemon)
        }
    }

    fun getAll() = dao.getAll()

    private val _s2 = MutableLiveData<List<Pokemon>>()
    val s2: LiveData<List<Pokemon>>
        get() = _s2

    init {
        _s2.value = listOf()
    }

    fun populateList() {
        val lista: ArrayList<Pokemon> = ArrayList()
        val jsonPokedex = URL("https://pokeapi.co/api/v2/pokemon?limit=1500&offset=0").readText()

        //aqui ele pega os tipos obitidos pela api
        val jsonObjPokedex = JSONObject(jsonPokedex)
        val resultsJsonPokedex = jsonObjPokedex.getJSONArray("results")
        for (i in 0..resultsJsonPokedex!!.length() - 1) {
            var pokemon=Pokemon(
                nome = resultsJsonPokedex.getJSONObject(i).getString("name"),
                tipo1 = "",
                tipo2 = "",
                id = 0,
                cor = 0,
                urlImagem = "",
                link = ""
            )

            lista.add(pokemon)
            _s2.value = _s2.value?.plus(pokemon)

            println("\n-------------------------------------------------------------------\n")
            println("pokemon: "+resultsJsonPokedex.getJSONObject(i).getString("name"))

        }


    }
}