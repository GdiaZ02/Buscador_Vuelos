package com.example.buscador_vuelos.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.ui.AppViewModelProvider
import com.example.buscador_vuelos.ui.AirportList
import com.example.buscador_vuelos.ui.FavoriteList
import com.example.buscador_vuelos.ui.FlightList
import com.example.buscador_vuelos.ui.LoadingIcon
import com.example.buscador_vuelos.ui.NoFavoriteList
import com.example.buscador_vuelos.ui.SearchTextField

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit = {},
    onAirportClicked: (Airport) -> Unit = {},
    onFavoriteClicked: (String, String) -> Unit = {_,_ -> },
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val searchUiState = viewModel.searchUiState.collectAsState().value
    val favoriteList by viewModel.favoriteList.collectAsState()
    val airportList by viewModel.airportList.collectAsState()

    Column(
        modifier = modifier
    ) {
        SearchTextField(
            searchQuery = searchUiState.searchQuery,
            onQueryChanged = onQueryChanged
        )

        when {
            searchUiState.isLoading -> {
                LoadingIcon()
            }
            searchUiState.searchQuery.isEmpty() && favoriteList.isEmpty() -> {
                NoFavoriteList()
            }
            searchUiState.searchQuery.isEmpty() -> {
                FavoriteList(
                    airportList = airportList,
                    favoriteList = favoriteList,
                    onFavoriteClicked = onFavoriteClicked
                )
            }
            searchUiState.selectedAirport != null -> {
                FlightList(
                    departureAirport = searchUiState.selectedAirport,
                    destinationList = airportList.filterNot { it.code == searchUiState.selectedAirport.code },
                    favoriteList = favoriteList,
                    onFavoriteClicked = onFavoriteClicked
                )
            }
            else -> {
                AirportList(
                    airportList = searchUiState.suggestionList,
                    onAirportClicked = onAirportClicked
                )
            }
        }
    }
}
