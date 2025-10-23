package com.example.notesapp.model

//Modelo de datos
//DataClass que representa una nota individual, es decir la plantilla o el molde
//Gurda la informacion de cada nota
//Aqui solo los datos
//Toda la logica pasara al viewmodel


//Para que se guraden los datos con Room, convertimosNote.kt en una entidad de Room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Indica que esta clase representa una tabla en la base de datos
@Entity(tableName = "notes_table")
data class Note(
    //El id se genera automaticamente
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var content: String = "",
    var isFavorite: Boolean = false
)

//Sin Room
/*data class Note(
    val id: Int,
    var title: String = "",
    var content: String = "",
    var isFavorite: Boolean = false
)*/

