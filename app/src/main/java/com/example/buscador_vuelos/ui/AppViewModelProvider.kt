package com.example.buscador_vuelos.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.buscador_vuelos.FlightApplication
import com.example.buscador_vuelos.ui.screen.SearchViewModel


object AppViewModelProvider {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)

            val flightRepository = application.container.flightRepository
            val preferencesRepository = application.userPreferencesRepository

            SearchViewModel(
                flightRepository = flightRepository,
                userPreferencesRepository = preferencesRepository
            )
        }
    }
}