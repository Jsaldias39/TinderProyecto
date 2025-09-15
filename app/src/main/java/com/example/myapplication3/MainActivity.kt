package com.example.myapplication3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.myapplication3.ui.theme.MyApplication3Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication3Theme {
                MainScreen(this)
            }
        }
    }
}

@Composable
fun MainScreen(activity: ComponentActivity) {
    val likedAlbums = remember { mutableStateListOf<Albumes>() }

    Box(modifier = Modifier.fillMaxSize()) {
        AlbumSliderManager(
            onAlbumLiked = { album ->
                if (!likedAlbums.contains(album)) likedAlbums.add(album)
            },
            activity = activity,
            likedAlbums = likedAlbums
        )
    }
}

@Composable
fun TinderImageSlider(
    images: List<String>,
    albumName: String,
    isLiked: Boolean,
    isRejected: Boolean,
    onLike: () -> Unit,
    onReject: () -> Unit,
    activity: ComponentActivity,
    likedAlbums: List<Albumes>
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(32.dp))
            ) {
                AsyncImage(
                    model = images[currentIndex],
                    contentDescription = "Albumes",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(32.dp))
                        .clickable {
                            currentIndex = (currentIndex + 1) % images.size
                        }
                )
                PaginationIndicator(
                    count = images.size,
                    currentIndex = currentIndex,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 12.dp)
                        .align(Alignment.TopCenter)
                )
                Text(
                    text = albumName,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Corazón",
                    tint = if (isLiked) Color(0xFFE91E63) else Color.White,
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onLike() }
                )
                Button(
                    onClick = {
                        val intent = Intent(activity, MainActivity2::class.java)
                        intent.putExtra("liked_albums", ArrayList(likedAlbums))
                        activity.startActivity(intent)
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    ),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Ir a Favoritos", color = Color.White)
                }
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "X",
                    tint = if (isRejected) Color.Red else Color.White,
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onReject() }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PaginationIndicator(
    count: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 0 until count) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (i == currentIndex) Color.White else Color.White.copy(alpha = 0.3f)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TinderImageSliderPreview() {
    MyApplication3Theme {
        val album = AlbumRepository.getAlbumDio()
        TinderImageSlider(
            images = album.imagenes,
            albumName = album.nombre,
            isLiked = false,
            isRejected = false,
            onLike = {},
            onReject = {},
            activity = ComponentActivity(),
            likedAlbums = emptyList()
        )
    }
}
