package com.example.buscador_vuelos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.buscador_vuelos.model.Airport
import com.example.buscador_vuelos.model.Favorite
import kotlinx.coroutines.flow.Flow


@Dao
interface FlightDao {
    @Query("SELECT * FROM airport ORDER BY id ASC")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :query OR name LIKE '%' || :query || '%' ORDER BY passengers DESC")
    fun getAllAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    suspend fun getFavoriteByInfo(departureCode: String, destinationCode: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}