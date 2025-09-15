package com.example.myapplication3

import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun AlbumSliderManager(
    onAlbumLiked: (Albumes) -> Unit,
    activity: ComponentActivity,
    likedAlbums: List<Albumes>
) {
    val allAlbums = listOf(
        AlbumRepository.getAlbumDio(),
        AlbumRepository.getAlbumMetallica(),
        AlbumRepository.getAlbumSaratoga(),
        AlbumRepository.getAlbumJohnnyCash(),
        AlbumRepository.getAlbumSteveRayVaugham(),
        AlbumRepository.getAlbumRataBlanca(),
        AlbumRepository.getAlbumQueen()
    )
    var activeAlbums by remember { mutableStateOf(allAlbums) }
    var shownIndices by remember { mutableStateOf(listOf<Int>()) }
    var currentIndex by remember { mutableStateOf((activeAlbums.indices).random()) }
    var isLiked by remember { mutableStateOf(false) }
    var isRejected by remember { mutableStateOf(false) }

    val currentAlbum = activeAlbums[currentIndex]

    LaunchedEffect(isLiked, isRejected) {
        if (isLiked) {
            onAlbumLiked(currentAlbum)
            delay(1500)
            val remaining = activeAlbums.indices.filter { it !in shownIndices + currentIndex }
            val nextIndex = if (remaining.isNotEmpty()) remaining.random() else activeAlbums.indices.random()
            shownIndices = if (remaining.isNotEmpty()) shownIndices + currentIndex else listOf()
            currentIndex = nextIndex
            isLiked = false
        }
        if (isRejected) {
            delay(1500)
            val updatedAlbums = activeAlbums.toMutableList()
            updatedAlbums.removeAt(currentIndex)
            if (updatedAlbums.isEmpty()) {
                activeAlbums = allAlbums
                shownIndices = listOf()
                currentIndex = (activeAlbums.indices).random()
            } else {
                activeAlbums = updatedAlbums
                shownIndices = listOf()
                currentIndex = (activeAlbums.indices).random()
            }
            isRejected = false
        }
    }

    TinderImageSlider(
        images = currentAlbum.imagenes,
        albumName = currentAlbum.nombre,
        isLiked = isLiked,
        isRejected = isRejected,
        onLike = { isLiked = true },
        onReject = { isRejected = true },
        activity = activity,
        likedAlbums = likedAlbums
    )
}
