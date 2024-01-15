package com.example.buscador_vuelos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.buscador_vuelos.R
import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.model.Favorite
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FlightList(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
    favoriteList: List<Favorite>,
    onFavoriteClicked: (String, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = stringResource(
                id = R.string.flights_from,
                departureAirport.code
            )
        )
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(
                items = destinationList
            ) { item ->
                val isFavorite = favoriteList.find {
                    it.departureCode == departureAirport.code && it.destinationCode == item.code
                }
                FlightItem(
                    isFavorite = isFavorite != null,
                    departureAirport = departureAirport,
                    destinationAirport = item,
                    onFavoriteClicked = onFavoriteClicked,
                )
            }
        }
    }
}


@Composable
fun FlightItem(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = true,
    departureAirport: Airport,
    destinationAirport: Airport,
    onFavoriteClicked: (String, String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.my_light_blue))
        ) {
            Column(
                modifier = modifier
                    .weight(10f)
            ) {
                Text(
                    text = stringResource(R.string.depart_label),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 32.dp)
                )
                AirportItem(
                    isClickable = true,
                    airport = departureAirport
                )
                Text(
                    text = stringResource(R.string.arrive_label),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 32.dp)
                )
                AirportItem(
                    isClickable = true,
                    airport = destinationAirport
                )
            }
            IconButton(
                onClick = {
                    onFavoriteClicked(departureAirport.code, destinationAirport.code)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    tint = if (isFavorite) colorResource(id = R.color.my_dark_orange) else Color.Gray,
                    contentDescription = null
                )
            }
        }
    }
}
