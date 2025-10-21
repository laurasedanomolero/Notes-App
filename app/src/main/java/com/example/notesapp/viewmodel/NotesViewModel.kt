package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notesapp.model.Note
import com.example.notesapp.model.NotesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel : ViewModel() {

    //Contador de las notas
    private var noteIdCounter = 0

    //Lista mutable donde se guardan las notas
    private val notesList = mutableListOf<Note>()

    //Estado interno (privado) mutable
    private val _uiState = MutableStateFlow(NotesUiState())

    //Estado publico e inmutable, la UI lo observa pero no puede cambiarlo directamente
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    //Cuando se crea el ViewModel se llama a loadNotes()
    init {
        loadNotes()
    }

    //Se actualiza el estado inicial con la lista de notas
    private fun loadNotes() {
        _uiState.value = _uiState.value.copy(
            notes = notesList
        )
    }

    // Funcion para crear notas
    fun createNote(): Note {
        noteIdCounter++
        val newNote = Note(id = noteIdCounter)
        notesList.add(newNote)
        _uiState.update { it.copy(notes = notesList.toList()) }
        return newNote
    }

    // Funcion para actualizar el titulo
    fun updateTitle(id: Int, newTitle: String) {
        val note = notesList.find { it.id == id }
        note?.let {
            it.title = newTitle
            _uiState.update { state -> state.copy(notes = notesList.toList()) }
        }
    }

    // Funcion para actualizar el contenido
    fun updateContent(id: Int, newContent: String) {
        val note = notesList.find { it.id == id }
        note?.let {
            it.content = newContent
            _uiState.update { state -> state.copy(notes = notesList.toList()) }
        }
    }

    //Funcion para eliminar una nota
    fun deleteNoteById(id: Int) {
        notesList.removeAll { it.id == id }
        _uiState.update { it.copy(notes = notesList.toList()) }
    }

    //Funcion para añadir la nota a favoritas
    fun toggleFavorite(id: Int) {
        val index = notesList.indexOfFirst { it.id == id }
        if (index != -1) {
            notesList[index] = notesList[index].copy(isFavorite = !notesList[index].isFavorite)
            _uiState.update { it.copy(notes = notesList.toList()) }
        }
    }
}
