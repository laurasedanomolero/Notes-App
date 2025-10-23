package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.ui.HomeScreen
import com.example.notesapp.ui.SecondScreen
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.ui.theme.NotesAppTheme

import com.example.notesapp.data.NoteDatabase

//Creamos la base de datos de Room
//Obtenemos el DAO (noteDao)
//Pasarlo al NotesViewModel manualmente, se puede hacer con factory (mirar mas adelante)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Creamos la instancia de la base de datos de Room
        val database = NoteDatabase.getDatabase(applicationContext)

        //Obtenemos el DAO para acceder a la tabla de notas
        val noteDao = database.noteDao()

        //Creamos el ViewModel pasándole el DAO directamente
        val notesViewModel = NotesViewModel(application)

        //Configuramos la interfaz
        setContent {
            NotesAppTheme {
                // ViewModel compartido entre pantallas
                //val notesViewModel: NotesViewModel = viewModel()

                // Estado de navegación
                var currentScreen by rememberSaveable { mutableStateOf("home") }
                var selectedNoteId by rememberSaveable { mutableStateOf<Int?>(null) }

                when (currentScreen) {
                    "home" -> {
                        HomeScreen(
                            onAddNoteClick = {
                                notesViewModel.createNote { newId ->
                                    selectedNoteId = newId
                                    currentScreen = "second"
                                }
                            },
                            onNoteClick = { noteId ->
                                selectedNoteId = noteId
                                currentScreen = "second"
                            },
                            viewModel = notesViewModel
                        )
                    }

                    "second" -> {
                        selectedNoteId?.let { id ->
                            SecondScreen(
                                noteId = id,
                                returnClick = { currentScreen = "home" },
                                deleteClick = {
                                    notesViewModel.deleteNoteById(id)
                                    currentScreen = "home"
                                },
                                viewModel = notesViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
