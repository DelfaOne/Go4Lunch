package com.fadel.go4lunch.ui.list.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.fadel.go4lunch.BuildConfig
import com.fadel.go4lunch.R
import com.fadel.go4lunch.ui.list.RestaurantsItemUiModel
import com.gowtham.ratingbar.RatingBar

@Preview(showBackground = true)
@Composable
fun RestaurantListItemPreview() {
    restaurantListItem(
        Modifier,
        RestaurantsItemUiModel(
            "Nom du restaurant",
            "Adresse du restaurant",
            "https://developer.android.com/images/brand/Android_Robot.png",
            "close at 7 pm",
            "123",
            "3",
            2.5,
            {
                ""
            },
        ),
        TODO()
    )
}

@Composable
fun restaurantListItem(
    modifier: Modifier,
    restaurantsItemUiModel: RestaurantsItemUiModel,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),

        ) {
        Box() {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier.weight(2f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = restaurantsItemUiModel.name,
                        style = MaterialTheme.typography.body1/*,
                    fontFamily = CustomsFont.RobotoFont.fontFamily*/
                    )
                    Text(
                        text = "${restaurantsItemUiModel.address}",
                        style = MaterialTheme.typography.body2/*,
                    fontFamily = CustomsFont.RobotoFont.fontFamily*/
                    )
                    Text(
                        text = restaurantsItemUiModel.openingHours,
                        style = MaterialTheme.typography.subtitle1,
                        /* fontFamily = CustomsFont.RobotoFont.fontFamily,*/
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp

                    )
                }
                Column(
                    modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = restaurantsItemUiModel.distance.toString()/*, fontFamily = CustomsFont.RobotoFont.fontFamily*/,
                        color = Color.Gray
                    )
                    colleagueCounter(interestNumber = restaurantsItemUiModel.interestNumber)
                    RatingBar(onValueChange = {}, onRatingChanged = {}, numStars = restaurantsItemUiModel.numberOfStars.toInt())
                }
                Image(
                    painter = rememberImagePainter(
                        data = photo(restaurantsItemUiModel),
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "Restaurant image",
                    modifier = modifier
                        .weight(1f)
                        .size(60.dp)
                )
            }
        }
    }
}

fun photo(restaurantsItemUiModel: RestaurantsItemUiModel): String {
    return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${restaurantsItemUiModel.photo}&key=${BuildConfig.GMP_KEY}".also {
    }

}



@Composable
fun colleagueCounter(
    interestNumber: String
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.Person, "")
        Text(text = interestNumber/*, fontFamily = CustomsFont.RobotoFont.fontFamily*/)
    }
}

sealed class CustomsFont() {
    abstract val fontFamily: FontFamily

    object RobotoFont : CustomsFont() {
        override val fontFamily: FontFamily
            get() = FontFamily(Font(R.font.roboto, FontWeight.Normal))
    }

}