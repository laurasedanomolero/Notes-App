package com.example.notesapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

// Extensión para acceder al DataStore
val Context.dataStore by preferencesDataStore(name = "notes_prefs")

object NotesStorage {
    private val NOTES_KEY = stringPreferencesKey("notes_list")
    private val gson = Gson()

    // Guardar (suspend)
    suspend fun saveNotes(context: Context, notes: List<Note>) {
        val json = gson.toJson(notes)
        context.dataStore.edit { prefs ->
            prefs[NOTES_KEY] = json
        }
    }

    // Cargar (suspend)
    suspend fun loadNotes(context: Context): MutableList<Note> {
        val prefs = context.dataStore.data.first()
        val json = prefs[NOTES_KEY]
        if (json.isNullOrBlank()) return mutableListOf()
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson<List<Note>>(json, type).toMutableList()
    }
}
