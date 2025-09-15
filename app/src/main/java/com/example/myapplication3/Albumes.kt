package com.example.myapplication3

import java.io.Serializable

data class Albumes(
    val nombre: String,
    val imagenes: List<String>
): Serializable
