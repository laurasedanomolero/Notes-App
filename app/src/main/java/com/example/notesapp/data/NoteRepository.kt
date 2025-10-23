package com.example.notesapp.data

//Creamos un repositorio
//Es como el puente entre el ViewModel y la base de datos
//Separa la logica de acceso a datos del retso de la app
//Permite que el ViewModel no dependa directamente del DAO

import com.example.notesapp.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note): Long {
        return noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
}
