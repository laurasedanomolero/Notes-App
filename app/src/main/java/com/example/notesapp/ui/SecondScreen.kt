package com.example.notesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.viewmodel.NotesViewModel

@Composable
fun SecondScreen(
    noteId: Int,
    returnClick: () -> Unit,
    deleteClick: () -> Unit,
    viewModel: NotesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val note = uiState.notes.find { it.id == noteId }

    if (note == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nota no encontrada.")
        }
        return
    }

    var title by rememberSaveable { mutableStateOf(note.title) }
    var content by rememberSaveable { mutableStateOf(note.content) }

    val grayPlaceholder = Color(0xFF777777)
    val lightPink = Color(0xFFFFF0F6) // rosa muy clarito 🌸

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 60.dp) // 👈 margen superior aumentado
    ) {
        //Fila superior de iconos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono volver
            IconButton(onClick = {
                viewModel.updateTitle(noteId, title)
                viewModel.updateContent(noteId, content)
                returnClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Icono favorito
            IconButton(onClick = { viewModel.toggleFavorite(noteId) }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favorito",
                    tint = if (note.isFavorite) Color(0xFFFFC1E3) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Icono eliminar
            IconButton(onClick = {
                viewModel.deleteNoteById(noteId)
                deleteClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Campo de título
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) { innerTextField ->
            if (title.isEmpty()) {
                Text("Título...", color = grayPlaceholder, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            innerTextField()
        }

        //Campo de contenido
        TextField(
            value = content,
            onValueChange = { content = it },
            placeholder = { Text("Escribe tu nota aquí...", color = grayPlaceholder) },
            modifier = Modifier
                .fillMaxSize(),
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = lightPink,
                unfocusedContainerColor = lightPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )
    }
}
