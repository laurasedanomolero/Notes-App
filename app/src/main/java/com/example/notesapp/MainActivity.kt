package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notesapp.ui.theme.NotesAppTheme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//Librerías para los tamaños y centrar los textos
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

//Librerias para los botones
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color //para los colores
import androidx.compose.ui.graphics.RectangleShape

//Libreria para los iconos de la botones
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Delete


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.modifier.modifierLocalConsumer

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.foundation.clickable




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                //estado donde se guarda en que pantalla estas, el valor incial home
                var currentScreen by remember { mutableStateOf("home") }
                //cuando clickas sobre una nota ya creada, se alamacena el id de las nota seleccionada para editarla
                var selectNoteId by remember { mutableStateOf<Int?>(null) }
                //lo de remember{mutableSateOf(...)} es para que las variables se puedan actualizar y la UI se refresque automaticamente

                if (currentScreen == "home") {
                    HomeScreen(
                        onAddNoteClick = {
                            val newNote = createNote()
                            selectNoteId = newNote.id
                            currentScreen = "sedcond"
                        },
                        onNoteClick = { noteId ->
                            selectNoteId = noteId
                            currentScreen = "sedcond"
                        }
                        )
                } else {
                    selectNoteId?.let { id ->
                        SecondScreen(
                            noteId = id,
                            returnClick = {currentScreen = "home"},
                            deleteClick = {
                                notesList.removeAll{ it.id == id}
                                currentScreen = "home"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onAddNoteClick: () -> Unit, onNoteClick: (Int) -> Unit) {
    Box(
        // ocupa toda la pantalla
        modifier = Modifier.fillMaxSize(),
        // centra el texto
        contentAlignment = Alignment.Center
    ) {
        //cuando no hay ninguna nota creada
        if(notesList.isEmpty()){
            //texto de inicio
            Text(
                text = "Hola, para escribir tu primera nota pulsa el +",
                //tamaño de la letra
                fontSize = 24.sp,
                //grosor de la fuente
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            )
        } else {
            //LazyColumn es como un listado vertical de cosas que se pueden deslizar hacia arriba
            //abajo. Lo de Lazy es porque solo dibuja en la pantalla lo que se ve, por eso perezoso,
            //es decir si hay 20 notas y solo caben 5 en la pantalla, solo dibuja las 5
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                //la siguiente linea es como decir por cada elemnto de notesList haz esto
                items(notesList) { note ->
                    //Card es una tarjeta visual
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            //clickable es una funcion que hace que la tarjeta se pueda clickar
                            //y cuando la clicka hace la funcion de dentro
                            .clickable{ onNoteClick(note.id) },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFC1E3)
                        )
                    ) {
                        Text(
                            text = if (note.title.isNotEmpty()) note.title else "(Sin título)",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        //Boton para añadir notas
        Button(
            //función para cambiar de pesteña
            onClick = { onAddNoteClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                // TopStart --> esquina superior izquierda
                // TopENd --> esquina superior derecha
                // BottomStart --> esquina inferior izquierda
                // BottomEnds --> esquina inderior izquierda
                .padding (end = 16.dp, bottom = 48.dp) //end es margen derecho y botton margen inferior
                .size(72.dp),
            colors = ButtonDefaults.buttonColors (
                containerColor = Color(0xFFFFC1E3)
                //Esto es para los colores, si pones containerColor para el fondo, y contentColor para el texto
            )
        ) {
            //dentro de las llaves es lo que se ve dentro del boton
            Text(
                text = "+", // el contenido del botón
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold //para hacer el + más gordito
            )
        }
    }
}

@Composable
fun SecondScreen(noteId: Int, returnClick: () -> Unit, deleteClick: () -> Unit){
    //variables donde se van almacenar todo lo que se escribe
    var text by remember { mutableStateOf("") }
    var title by remember {mutableStateOf (value = "")}
    //variable para encontar la nota correspondiente de la lista segun su id+
    val note = notesList.find {it.id == noteId}

    Box(
        modifier = Modifier
            // ocupa toda la pantalla
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 64.dp, start = 16.dp, end = 16.dp),
    ) {
        //Botones y titulo de la parte superior
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Boton volver
            Button(
                onClick = {
                    updateTitle(noteId, title)
                    updateContet(noteId, text)
                    returnClick()
                },
                modifier = Modifier
                    .padding(
                        end = 16.dp,
                        bottom = 10.dp
                    )
                    .size(
                        width = 90.dp,
                        height = 60.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC1E3)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    // color del ícono
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Botón borrar
            Button(
                onClick = { deleteClick() },
                modifier = Modifier
                    .padding(
                        end = 16.dp,
                        bottom = 10.dp
                    )
                    .size(
                        width = 90.dp,
                        height = 60.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC1E3)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Titulo
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Título...") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFC1E3),
                    unfocusedContainerColor = Color(0xFFFFC1E3),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                )
            )
        }

        // Campo de texto donde se va a escribir
        androidx.compose.material3.TextField(
            value = text,
            onValueChange = {
                text = it
            }, // cada vez que se escribe algo, se actualiza el valor de text con lo nuevo
            // lo que se pone cuando el valor de texto es nulo
            placeholder = { Text("Escribe tu nota aquí...") },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 92.dp),
            // permite que el texto tenga varias lineas
            singleLine = false,
            // numero ilimitado de lineas
            maxLines = Int.MAX_VALUE,
            colors = TextFieldDefaults.colors(
                //aqui se pone focused y unfocused porque para el textField hay más estados que para el buttom
                //cuando no esta el cursor encima
                focusedContainerColor = Color(0xFFFFC1E3),
                //cuando no esta el cursor encima
                unfocusedContainerColor = Color(0xFFFFC1E3),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Preview Home"
)
@Composable
fun PreviewHome() {
    // Simulamos algunas notas para ver cómo queda el diseño
    notesList.clear()
    notesList.addAll(
        listOf(
            Note(id = 1, title = "Comprar pan", content = "Ir a la panadería a las 9"),
            Note(id = 2, title = "Estudiar Kotlin", content = "Repasar clases y hacer un ejemplo"),
            Note(id = 3, title = "Ir al gym", content = "Pierna y hombros")
        )
    )

    NotesAppTheme {
        HomeScreen(
            onAddNoteClick = {},   // en el preview no hace nada
            onNoteClick = {}       // en el preview no hace nada
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Preview Second"
)
@Composable
fun PreviewSecond() {
    // Simulamos una nota para editar
    val fakeNote = Note(id = 99, title = "Nota de prueba", content = "Este es el contenido")
    notesList.clear()
    notesList.add(fakeNote)

    NotesAppTheme {
        SecondScreen(
            noteId = fakeNote.id,
            returnClick = {},   // en el preview no hace nada
            deleteClick = {}    // en el preview no hace nada
        )
    }
}
