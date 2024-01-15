package com.example.buscador_vuelos.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buscador_vuelos.data.FlightRepository
import com.example.buscador_vuelos.data.UserPreferencesRepository
import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.model.Favorite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(
    val flightRepository: FlightRepository,
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    val favoriteList: StateFlow<List<Favorite>> =
        flightRepository.getAllFavorites()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    val airportList: StateFlow<List<Airport>> =
        flightRepository.getAllAirports()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyList()
            )

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences.collect {
                searchFromQuery(it.searchValue)
                checkLoadingStatus()
            }
        }
    }

    private suspend fun checkLoadingStatus() {
        delay(200)
        _searchUiState.update { it.copy(isLoading = airportList.value.isEmpty()) }
    }


    fun onQueryChanged(searchQuery: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreferences(searchValue = searchQuery)
        }

        searchFromQuery(searchQuery)
    }

    private fun searchFromQuery(searchQuery: String) {
        _searchUiState.update { it.copy(searchQuery = searchQuery) }

        viewModelScope.launch {
            if (searchQuery.isEmpty()) {
                _searchUiState.update { it.copy(suggestionList = emptyList()) }
            } else {
                flightRepository.getAllAirports(searchQuery).collect { airportList ->
                    _searchUiState.update { it.copy(suggestionList = airportList) }
                }
            }
        }
    }


    fun updateSelectedAirport(selectedAirport: Airport?) {
        _searchUiState.update { it.copy(selectedAirport = selectedAirport) }
    }

    fun onAirportClicked(selectedAirport: Airport) {
        viewModelScope.launch {

            _searchUiState.update {
                searchUiState.value.copy(
                    searchQuery = selectedAirport.code,
                    selectedAirport = selectedAirport,
                    suggestionList = emptyList()
                )
            }

            userPreferencesRepository.saveUserPreferences(searchValue = selectedAirport.code)
        }
    }


    fun onFavoriteClicked(departureCode: String, destinationCode: String) {
        viewModelScope.launch {

            flightRepository.getFavoriteByInfo(departureCode, destinationCode)?.let { favorite ->
                flightRepository.deleteFavorite(favorite)
            } ?: flightRepository.insertFavorite(Favorite(departureCode = departureCode, destinationCode = destinationCode))
        }
    }
}

