package com.fadel.go4lunch.ui.detail

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.gowtham.ratingbar.RatingBar

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        RestaurantDetailUiModel(
            name = "Kebab du moulin",
            address = "1600 Amphitheatre Parkway, Mountain View, CA 94043, Etats-Unis",
            id = "123456",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Cat_November_2010-1a.jpg/1200px-Cat_November_2010-1a.jpg",
            rating = 2F,
            phoneNumber = "06464454332",
            website = "www.google.fr"
        )
    )
}

@Composable
fun DetailScreenHandler(
    vm: DetailViewModel
) {
    val viewState: RestaurantDetailUiModel? by vm.getViewStateLiveData().observeAsState()
    viewState?.let {
        DetailScreen(viewState = it)
    }

}


@Composable
fun DetailScreen(
    viewState: RestaurantDetailUiModel
) {

    Box(
        modifier = Modifier
            .background(color = Colors.orangeColor), contentAlignment = Alignment.TopCenter
    ) {

        Column {
            Image(
                painter = rememberImagePainter(
                    data = viewState.imageUrl,
                ),
                contentDescription = "Illustration",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${viewState.name}",
                    Modifier.padding(10.dp),
                    color = Color.Black
                )
                RatingBar(
                    onValueChange = {},
                    onRatingChanged = {},
                    value = viewState.rating,
                    size = 14.dp
                )
            }
            Text(
                text = "${viewState.address}",
                Modifier.padding(10.dp),
                color = Color.Black
            )

            InteractComponents(onClick = {}, viewState.phoneNumber, viewState.website)
        }
    }
}

@Composable
fun InteractComponents(
    onClick: () -> Unit,
    phoneNumber: String,
    website: String
) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {
                startActivity(
                    context,
                    Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:$phoneNumber") },
                    null
                )
            }
            ) {
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
            IconButton(onClick = {

                startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(website)), null)
            }) {
                Icon(Icons.Filled.Public, contentDescription = "WEBSITE", tint = Colors.orangeColor)
            }
            Text(text = "WEBSITE", color = Colors.orangeColor, textAlign = TextAlign.Center)
        }
    }
}

sealed class Colors {
    companion object {
        val orangeColor: Color = Color(217, 96, 0)
    }
}

