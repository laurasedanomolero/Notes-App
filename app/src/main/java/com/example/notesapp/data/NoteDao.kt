package com.example.notesapp.data

//Creamos el DAO (Data Access Object) para guardar la informacion con Room

import androidx.room.*
import com.example.notesapp.model.Note
import kotlinx.coroutines.flow.Flow

//Marca la interfaz como un Data Acces Object
@Dao
interface NoteDao {

    //Devuelve los datos como flujo observable (Compose puede escucharlos)
    @Query("SELECT * FROM notes_table ORDER BY isFavorite DESC")
    fun getAllNotes(): Flow<List<Note>>

    //Estos metodos son automaticos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note:Note)

    @Query("SELECT * FROM notes_table WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: Int): Note?
}