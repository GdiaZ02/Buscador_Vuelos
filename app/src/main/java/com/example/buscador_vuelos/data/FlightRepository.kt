package com.example.buscador_vuelos.data

import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow


interface FlightRepository {

    fun getAllAirports(): Flow<List<Airport>>


    fun getAllAirports(query: String): Flow<List<Airport>>


    fun getAllFavorites(): Flow<List<Favorite>>


    suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite?


    suspend fun insertFavorite(favorite: Favorite)


    suspend fun deleteFavorite(favorite: Favorite)
}