package br.com.dio.cartaodevisita.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.dio.cartaodevisita.databinding.ActivityMainBinding
import android.graphics.Color

import android.graphics.drawable.ColorDrawable
import org.json.JSONObject
import java.net.URL
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import br.com.dio.cartaodevisita.App
import br.com.dio.cartaodevisita.util.Image
import com.google.android.gms.security.ProviderInstaller


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }
    private val adapter by lazy { PokemonAdapter() }

    private val binding  by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpPermissions()
        binding.rvCards.adapter = adapter
        getAllCards()
        insertListeners()

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        ProviderInstaller.installIfNeeded(getApplicationContext());

       /* val json = URL("https://pokeapi.co/api/v2/pokemon/150").readText()

        //aqui ele pega os tipos obitidos pela api
        val jsonObj = JSONObject(json)
        val foodJson = jsonObj.getJSONArray("types")
        for (i in 0..foodJson!!.length() - 1) {

            val types = JSONObject(foodJson.getJSONObject(i).getString("type"))

            println("tipo: "+types.getString("name"))
        }

        //pega nome do pokemon
        val formsJson = jsonObj.getJSONArray("forms")
        val pokemon =  formsJson.getJSONObject(0).getString("name")
        println("pokemon: "+pokemon)


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
            corsetar=Color.rgb(26,26,26)
        }else if(cor.equals("blue")){
            corsetar=Color.rgb(4, 102, 200)
        }else if(cor.equals("brown")){
            corsetar=Color.rgb(64, 48, 36)
        }else if(cor.equals("gray")){
            corsetar=Color.rgb(173, 181, 189)
        }else if(cor.equals("green")){
            corsetar=Color.rgb(74, 214, 109)
        }else if(cor.equals("pink")){
            corsetar=Color.rgb(255, 143, 163)
        }else if(cor.equals("purple")){
            corsetar=Color.rgb(90, 24, 154)
        }else if(cor.equals("red")){
            corsetar=Color.rgb(195, 47, 39)
        }else if(cor.equals("white")){
            corsetar=Color.rgb(232, 232, 232)
        }else if(cor.equals("yellow")){
            corsetar=Color.rgb(237, 205, 78)
        }else{
            corsetar=Color.WHITE
        }
        println("cor: "+cor)

        binding.root.background = ColorDrawable(corsetar)*/


        val jsonPokedex = URL("https://pokeapi.co/api/v2/pokemon?limit=1500&offset=0").readText()

        //aqui ele pega os tipos obitidos pela api
        val jsonObjPokedex = JSONObject(jsonPokedex)
        val resultsJsonPokedex = jsonObjPokedex.getJSONArray("results")
        for (i in 0..resultsJsonPokedex!!.length() - 1) {


            println("\n-------------------------------------------------------------------\n")
            println("pokemon: "+resultsJsonPokedex.getJSONObject(i).getString("name"))

        }

    }

    private fun setUpPermissions() {
        // write permission to access the storage
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddCartaoDeVisita::class.java)
            startActivity(intent)
        }
        adapter.listenerShare = { card ->
            Image.share(this@MainActivity, card)
        }
    }

    private fun getAllCards() {
        mainViewModel.getAll().observe(this, { cards ->
            adapter.submitList(cards)
        })
    }

}