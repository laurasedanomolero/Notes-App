package com.example.notesapp.model

//Modelo de datos
//DataClass que representa una nota individual, es decir la plantilla o el molde
//Gurda la informacion de cada nota
//Aqui solo los datos
//Toda la logica pasara al viewmodel

data class Note(
    val id: Int,
    var title: String = "",
    var content: String = "",
    var isFavorite: Boolean = false
)

