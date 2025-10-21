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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NotesAppTheme {
                // ViewModel compartido entre pantallas
                val notesViewModel: NotesViewModel = viewModel()

                // Estado de navegación
                var currentScreen by rememberSaveable { mutableStateOf("home") }
                var selectedNoteId by rememberSaveable { mutableStateOf<Int?>(null) }

                when (currentScreen) {
                    "home" -> {
                        HomeScreen(
                            onAddNoteClick = {
                                val newNote = notesViewModel.createNote()
                                selectedNoteId = newNote.id
                                currentScreen = "second"
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
