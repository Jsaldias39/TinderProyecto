package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.myapplication3.ui.theme.MyApplication3Theme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.IconButton
import androidx.compose.foundation.clickable

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val likedAlbums = intent.getSerializableExtra("liked_albums") as? ArrayList<Albumes> ?: arrayListOf()
        setContent {
            MyApplication3Theme {
                LikedAlbumsScreen(likedAlbums, this)
            }
        }
    }
}

@Composable
fun LikedAlbumsScreen(albums: MutableList<Albumes>, activity: ComponentActivity? = null) {
    var likedAlbums by remember { mutableStateOf(albums) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
        ) {
            items(likedAlbums) { album ->
                AlbumItem(
                    album = album,
                    onRemove = {
                        likedAlbums = likedAlbums.toMutableList().apply { remove(album) }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Botón Volver al Inicio al final de la lista
            item {
                if (likedAlbums.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Button(
                    onClick = {
                        activity?.finish() // Cierra esta actividad y vuelve a MainActivity
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63) // Mismo color que el botón de favoritos
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                ) {
                    Text("Volver al Inicio", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Mensaje cuando no hay álbumes favoritos
        if (likedAlbums.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No tienes álbumes favoritos",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        activity?.finish() // Vuelve al inicio
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                ) {
                    Text("Volver al Inicio", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun AlbumItem(album: Albumes, onRemove: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tarjeta del álbum
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.DarkGray)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = album.imagenes.firstOrNull(),
                    contentDescription = album.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(
                    text = album.nombre,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Botón X debajo del álbum (alineado a la derecha)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Quitar de favoritos",
                tint = Color.Red, // Rojo como en MainActivity1
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onRemove() }
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikedAlbumsScreenPreview() {
    MyApplication3Theme {
        LikedAlbumsScreen(
            albums = mutableListOf(
                AlbumRepository.getAlbumDio(),
                AlbumRepository.getAlbumMetallica(),
                AlbumRepository.getAlbumQueen()
            )
        )
    }
}