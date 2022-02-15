package com.fadel.go4lunch.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.fadel.go4lunch.R
import com.fadel.go4lunch.ui.list.ListViewModel
import com.fadel.go4lunch.ui.list.RestaurantsItemUiModel
import com.gowtham.ratingbar.RatingBar

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        name = "Kebab du moulin",
        address = "1600 Amphitheatre Parkway, Mountain View, CA 94043, Etats-Unis",
        numberOfStars = 4F,
        "url"
    )
}

@Composable
fun DetailScreen(
    name: String?,
    address: String?,
    numberOfStars: Float?,
    urlPicture: String?
) {
    Box(
        modifier = Modifier
            .background(color = Colors.orangeColor), contentAlignment = Alignment.TopCenter
    ) {

        Column {
            Image(
                painter = rememberImagePainter(
                    data = photo(urlPicture),
                ),
                contentDescription = "Illustration",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$name",
                    Modifier.padding(10.dp),
                    color = Color.Black
                )
                RatingBar(
                    onValueChange = {},
                    onRatingChanged = {},
                    value = numberOfStars ?: 0.0F,
                    size = 14.dp
                )
            }
            Text(
                text = "$address",
                Modifier.padding(10.dp),
                color = Color.Black
            )

            InteractComponents(onClick = {})
        }
    }
}

@Composable
fun InteractComponents(
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { onClick }) {
                Icon(Icons.Filled.Call, contentDescription = "Call", tint = Colors.orangeColor)
            }
            Text(text = "CALL", color = Colors.orangeColor)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { onClick }) {
                Icon(Icons.Filled.Star, contentDescription = "LIKE", tint = Colors.orangeColor)
            }
            Text(text = "LIKE", color = Colors.orangeColor, textAlign = TextAlign.Center)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { onClick }) {
                Icon(Icons.Filled.Public, contentDescription = "WEBSITE", tint = Colors.orangeColor)
            }
            Text(text = "WEBSITE", color = Colors.orangeColor, textAlign = TextAlign.Center)
        }
    }
}

fun photo(urlPicture: String?): String {
    return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${urlPicture}&key=${ListViewModel.apiKey}".also {
    }

}

sealed class Colors {
    companion object {
        val orangeColor: Color = Color(217, 96, 0)
    }
}

