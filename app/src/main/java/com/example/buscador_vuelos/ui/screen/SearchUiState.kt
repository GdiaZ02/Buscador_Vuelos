package com.example.buscador_vuelos.ui.screen

import com.example.buscador_vuelos.model.Airport

data class SearchUiState(
    val searchQuery: String = "",
    val suggestionList: List<Airport> = emptyList(),
    val selectedAirport: Airport? = null,
    val isLoading: Boolean = true
)