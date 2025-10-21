package com.example.notesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NotesViewModel

@Composable
fun HomeScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Int) -> Unit,
    viewModel: NotesViewModel = viewModel()
) {
    //Estado, observa el uiState en tiempo real
    //y guarda si esta mostrando solo favorritos o todas las notas
    val uiState by viewModel.uiState.collectAsState()
    val allNotes = uiState.notes
    var showFavoritesOnly by remember { mutableStateOf(false) }

    val notes = if (showFavoritesOnly) allNotes.filter { it.isFavorite } else allNotes

    //Colores personalizados
    val lightGray = Color(0xFFF5F5F5)
    val softPink = Color(0xFFFFE4EF) // rosa clarito para la cabecera
    val pink = Color(0xFFFFC1E3)     // rosa botón +
    val grayText = Color(0xFF777777)

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
            ) {
                // Botón de favoritos
                FloatingActionButton(
                    onClick = { showFavoritesOnly = !showFavoritesOnly },
                    containerColor = if (showFavoritesOnly) Color.Yellow else pink
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Favoritos",
                        tint = if (showFavoritesOnly) Color.Black else Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Botón de añadir nota
                FloatingActionButton(
                    onClick = onAddNoteClick,
                    containerColor = pink
                ) {
                    Text("+", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .systemBarsPadding()
        ) {
            // Cabecera rosa pegada arriba
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(softPink),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Notas",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de notas
            if (notes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (showFavoritesOnly)
                            "No tienes notas favoritas aún"
                        else
                            "No hay notas todavía",
                        color = softPink
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(notes) { note ->
                        NoteItem(
                            note = note,
                            onNoteClick = { onNoteClick(note.id) },
                            onDeleteClick = { viewModel.deleteNoteById(note.id) },
                            onFavoriteClick = { viewModel.toggleFavorite(note.id) },
                            lightGray = lightGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    lightGray: Color
) {
    val pinkLight = Color(0xFFFFE4EF) // rosa clarito para el contenido
    val pink = Color(0xFFFFC1E3)      // rosa para favorito

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onNoteClick() },
        colors = CardDefaults.cardColors(containerColor = lightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (note.title.isNotEmpty()) note.title else "(Sin título)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                color = pinkLight, // contenido rosa clarito
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onFavoriteClick) {
                    Text(
                        text = if (note.isFavorite) "★ Favorito" else "☆ Favorito",
                        color = if (note.isFavorite) pink else Color.Gray
                    )
                }
                TextButton(onClick = onDeleteClick) {
                    Text("Eliminar")
                }
            }
        }
    }
}
