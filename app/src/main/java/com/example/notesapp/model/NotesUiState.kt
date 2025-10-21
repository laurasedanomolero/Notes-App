package com.example.notesapp.model

//data class, pero que es toda la informacion que la interfaz necesita mostrar
//en este caso la UI necesita saber que notas existen,
// //para eso las vamos guardando en una lista

//conecta la capa de datos con la interfaz grafica (compose)
//es el observable que usa la UI

data class NotesUiState(
    val notes: List<Note> = emptyList()
)
