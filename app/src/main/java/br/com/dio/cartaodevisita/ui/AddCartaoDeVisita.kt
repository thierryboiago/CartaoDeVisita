package br.com.dio.cartaodevisita.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.dio.cartaodevisita.databinding.ActivityMainBinding

class AddCartaoDeVisita : AppCompatActivity() {


    private val binding  by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}