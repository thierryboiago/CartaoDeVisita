package br.com.dio.cartaodevisita.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.dio.cartaodevisita.databinding.ActivityMainBinding
import android.graphics.Color

import android.graphics.drawable.ColorDrawable
import org.json.JSONObject
import java.net.URL
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy


class MainActivity : AppCompatActivity() {


    private val binding  by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val json = URL("https://pokeapi.co/api/v2/pokemon/150").readText()

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
            corsetar=Color.BLACK
        }else if(cor.equals("blue")){
            corsetar=Color.BLUE
        }else if(cor.equals("brown")){
            corsetar=Color.rgb(64, 33, 6)
        }else if(cor.equals("gray")){
            corsetar=Color.GRAY
        }else if(cor.equals("green")){
            corsetar=Color.GREEN
        }else if(cor.equals("pink")){
            corsetar=Color.rgb(222, 35, 222)
        }else if(cor.equals("purple")){
            corsetar=Color.rgb(105,50,68)
        }else if(cor.equals("red")){
            corsetar=Color.RED
        }else if(cor.equals("white")){
            corsetar=Color.WHITE
        }else if(cor.equals("yellow")){
            corsetar=Color.YELLOW
        }else{
            corsetar=Color.WHITE
        }
        println("cor: "+cor)

        binding.root.background = ColorDrawable(corsetar)


    }

}