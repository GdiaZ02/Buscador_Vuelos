package com.example.buscador_vuelos

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buscador_vuelos.ui.AppViewModelProvider
import com.example.buscador_vuelos.ui.screen.SearchScreen
import com.example.buscador_vuelos.ui.screen.SearchViewModel
import com.example.buscador_vuelos.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.flight_search),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.my_blue)
                )
            )
        }
    )
    {
        val focusManager = LocalFocusManager.current

        val viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)

        SearchScreen(
            modifier = Modifier.padding(it),
            onQueryChanged = { query ->
                viewModel.updateSelectedAirport(null)
                viewModel.onQueryChanged(query)
                if(query.isEmpty()) focusManager.clearFocus()
            },
            onAirportClicked = { airport ->
                viewModel.updateSelectedAirport(airport)
                viewModel.onAirportClicked(airport)
                focusManager.clearFocus()
            },
            onFavoriteClicked = { depCode, destCode ->
                viewModel.onFavoriteClicked(depCode, destCode)
            }
        )
    }
}

