package com.fadel.go4lunch.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar


@Composable
fun DetailScreen(
    name: String?,
    address: String?,
    numberOfStars: Float?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.Center
    ) {

        Column {
            Text(
                text = "Nom du restaurant : $name",
                Modifier.padding(10.dp),
                color = Color.Black
            )
            Text(text = "Addresse du restaurant $address", Modifier.padding(10.dp), color = Color.Black)
            RatingBar(onValueChange = {}, onRatingChanged = {}, value = numberOfStars ?: 0.0F)
        }
    }
}