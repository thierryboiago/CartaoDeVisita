package br.com.dio.cartaodevisita.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey val id: Int,
    val nome: String,
    val tipo1: String,
    val tipo2: String,
    val urlImagem: String,
    val cor: Int,
    val link: String,
){

}