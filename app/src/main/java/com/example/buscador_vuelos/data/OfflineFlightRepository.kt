package com.example.buscador_vuelos.data

import com.example.buscador_vuelos.data.FlightDao
import com.example.buscador_vuelos.data.FlightRepository
import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow


class OfflineFlightRepository(
    private val flightDao: FlightDao
) : FlightRepository {

    override fun getAllAirports(): Flow<List<Airport>> {
        return flightDao.getAllAirports()
    }

    override fun getAllAirports(query: String): Flow<List<Airport>> {
        return flightDao.getAllAirports(query)
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return flightDao.getAllFavorites()
    }

    override suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite? {
        return flightDao.getFavoriteByInfo(departureCode, destinationCode)
    }
    override suspend fun insertFavorite(favorite: Favorite) {
        return flightDao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        return flightDao.deleteFavorite(favorite)
    }
}