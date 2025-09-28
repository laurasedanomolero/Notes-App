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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                //estado donde se guarda en que pantalla estas, el valor incial home
                var currentScreen by remember { mutableStateOf("home") }

                if (currentScreen == "home") {
                    HomeScreen( onAddNoteClick = {currentScreen = "sedcond" })
                } else {
                    SecondScreen(
                        returnClick = {currentScreen = "home"},
                        deleteClick = {currentScreen = "home"}
                        )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onAddNoteClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(), // ocupa toda la pantalla
        contentAlignment = Alignment.Center // centra el texto
    ) {
        //texto de inicio
        Text(
            text = "Hola, para escribir tu primera nota pulsa el +",
            fontSize = 24.sp, //tamaño de la letra
            fontWeight = FontWeight.Bold, //grosor de la fuente
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        )

        //Boton para añadir notas
        Button(
            onClick = { onAddNoteClick() }, //función para cambiar de pesteña
            modifier = Modifier
                .align(Alignment.BottomEnd) // esquina inferior izquierda
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
fun SecondScreen(returnClick: () -> Unit, deleteClick: () -> Unit){
    //variables donde se va almacenartodo lo que se escribe
    var text by remember { mutableStateOf("") }
    var title by remember {mutableStateOf (value = "")}

    Box(
        modifier = Modifier
            .fillMaxSize() // ocupa toda la pantalla
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
                onClick = { returnClick() },
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
                    tint = Color.Black, // color del ícono
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
            placeholder = { Text("Escribe tu nota aquí...") }, // lo que se pone cuando el valor de texto es nulo
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 92.dp),
            singleLine = false, // permite que el texto tenga varias lineas
            maxLines = Int.MAX_VALUE, // numero ilimitado de lineas
            colors = TextFieldDefaults.colors(
                //aqui se pone focused y unfocused porque para el textField hay más estados que para el buttom
                focusedContainerColor = Color(0xFFFFC1E3), //cuando no esta el cursor encima
                unfocusedContainerColor = Color(0xFFFFC1E3), //cuando no esta el cursor encima
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
    name = "My Preview")
@Composable
fun PreviewHome() {
    NotesAppTheme {
        HomeScreen(onAddNoteClick = {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "My Preview")
@Composable
fun PreviewSecond() {
    NotesAppTheme {
        SecondScreen(returnClick = {}, deleteClick = {})
    }
}