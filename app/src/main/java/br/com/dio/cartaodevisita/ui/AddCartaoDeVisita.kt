package br.com.dio.cartaodevisita.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import br.com.dio.cartaodevisita.App
import br.com.dio.cartaodevisita.R
import br.com.dio.cartaodevisita.data.Pokemon
import br.com.dio.cartaodevisita.databinding.ActivityAddPokemonBinding
import br.com.dio.cartaodevisita.databinding.ActivityMainBinding
import br.com.dio.cartaodevisita.util.Image
import org.json.JSONObject
import java.net.URL
import java.util.*

class AddCartaoDeVisita : AppCompatActivity() {


    private val binding  by lazy { ActivityAddPokemonBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }
    private val adapter by lazy { PokemonListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding.rvListPokemon.adapter = adapter
        insertListeners()
        getAllPokemons()
    }

    private fun insertListeners() {

        adapter.listenerShare = { card ->
            //click para selecionar esse
            val poke: TextView = card.findViewById(R.id.tvNomePokemon)
            val json = URL("https://pokeapi.co/api/v2/pokemon/"+ poke.text.toString().lowercase(Locale.getDefault())).readText()

            //aqui ele pega os tipos obitidos pela api
            val jsonObj = JSONObject(json)
            val typesJson = jsonObj.getJSONArray("types")
            var contador =0
            var tipo1 = ""
            var tipo2 = ""
            for (i in 0..typesJson!!.length() - 1) {
                val types = JSONObject(typesJson.getJSONObject(i).getString("type"))
                println("tipo: "+types.getString("name"))
                if(contador == 0){
                    tipo1 = types.getString("name")
                }else if(contador == 1){
                    tipo2 = types.getString("name")
                }

                contador++

            }

            //pega nome do pokemon
            val formsJson = jsonObj.getJSONArray("forms")
            val pokemonNome =  formsJson.getJSONObject(0).getString("name")
            println("pokemon: "+pokemonNome)


            //pega link da foto
            val foto =  jsonObj.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")
            println("foto: "+foto)



            //pega specie
            val species =  jsonObj.getJSONObject("species").getString("url")
            println("species: "+species)
            val jsonSpecie = URL(species).readText()
            val jsonObjSpecie = JSONObject(jsonSpecie)
            val cor =  jsonObjSpecie.getJSONObject("color").getString("name")
            var corsetar: Int
            if(cor.equals("black")){
                corsetar= Color.rgb(26,26,26)
            }else if(cor.equals("blue")){
                corsetar= Color.rgb(4, 102, 200)
            }else if(cor.equals("brown")){
                corsetar= Color.rgb(64, 48, 36)
            }else if(cor.equals("gray")){
                corsetar= Color.rgb(173, 181, 189)
            }else if(cor.equals("green")){
                corsetar= Color.rgb(74, 214, 109)
            }else if(cor.equals("pink")){
                corsetar= Color.rgb(255, 143, 163)
            }else if(cor.equals("purple")){
                corsetar= Color.rgb(90, 24, 154)
            }else if(cor.equals("red")){
                corsetar= Color.rgb(195, 47, 39)
            }else if(cor.equals("white")){
                corsetar= Color.rgb(232, 232, 232)
            }else if(cor.equals("yellow")){
                corsetar= Color.rgb(237, 205, 78)
            }else{
                corsetar= Color.WHITE
            }
            println("cor: "+cor)

            val re = Regex("[^0-9]")
            val idd = re.replace(foto, "").toInt()
            val pokemon = Pokemon(
                nome = pokemonNome,
                tipo1 = tipo1,
                tipo2 = tipo2,
                id = idd,
                cor = corsetar,
                urlImagem = foto,
                link = "https://pokeapi.co/api/v2/pokemon/"+poke.text.toString().lowercase(Locale.getDefault())
            )
            mainViewModel.insert(pokemon)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun getAllPokemons() {
        mainViewModel.getAllPokemonList().observe(this, {
            println("passa aq")
            adapter.submitList(it)
        })
    }
}