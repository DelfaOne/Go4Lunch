package com.fadel.go4lunch.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.fadel.go4lunch.ui.list.RestaurantsItemUiModel

@Preview(showBackground = true)
@Composable
fun RestaurantListPreview() {
    restaurantList(
        List(10) {
            RestaurantsItemUiModel(
                "Nom du restaurant",
                "Adresse du restaurant",
                "https://developer.android.com/images/brand/Android_Robot.png",
                "close at 7 pm",
                "123",
                "3",
                2.5,
                null,
            )
        },
        Modifier,
    )
}

@Composable
fun restaurantList(
    restaurantsItemUiModels: List<RestaurantsItemUiModel>,
    modifier: Modifier,
) {
    Box(modifier) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            state = rememberLazyListState()
        ) {
            restaurantsItemUiModels.forEach {
                item {
                    restaurantListItem(
                        modifier = Modifier,
                        it
                    ) {
                        it.onItemClicked?.invoke()
                    }
                }
            }
        }
    }
}
