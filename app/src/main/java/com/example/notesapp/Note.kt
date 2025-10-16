package com.example.notesapp
import androidx.compose.runtime.mutableStateListOf

var noteIdCounter: Int = 0

data class Note (
    val id: Int,
    var title: String = "",
    var content: String = "",
    var isFavorite: Boolean = false
)

val notesList = mutableStateListOf<Note>()

fun createNote(): Note {
    noteIdCounter++
    val newNote = Note(id = noteIdCounter)
    notesList.add(newNote)
    return newNote
}

fun updateTitle(id : Int, newTitle: String){
    val note = notesList.find {it.id == id}
    note?.let{
        it.title = newTitle
    }
}

fun updateContent(id : Int, newContent: String){
    val note = notesList.find {it.id == id}
    note?.let{
        it.content = newContent
    }
}