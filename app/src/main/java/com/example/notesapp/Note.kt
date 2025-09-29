package com.example.notesapp

var noteIdCounter: Int = 0

data class Note (
    val id: Int,
    var title: String = "",
    var content: String = ""
)

val notesList = mutableListOf<Note>()

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

fun updateContet(id : Int, newContent: String){
    val note = notesList.find {it.id == id}
    note?.let{
        it.content = newContent
    }
}