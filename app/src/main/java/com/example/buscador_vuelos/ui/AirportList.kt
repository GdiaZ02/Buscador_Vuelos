package com.example.buscador_vuelos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.R


@Composable
fun AirportList(
    modifier: Modifier = Modifier,
    airportList: List<Airport>,
    onAirportClicked: (Airport) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        items(
            items = airportList
        ) {
            Card(
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                AirportItem(
                    modifier = Modifier
                        .background(colorResource(id = R.color.my_light_blue)),
                    airport = it,
                    onAirportClicked = onAirportClicked
                )
            }
        }
    }
}


@Composable
fun AirportItem(
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    airport: Airport,
    onAirportClicked: (Airport) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(enabled = isClickable) {
                if (airport.code != "") onAirportClicked(airport)
            }
    ) {
        Text(
            text = airport.code,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = airport.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

