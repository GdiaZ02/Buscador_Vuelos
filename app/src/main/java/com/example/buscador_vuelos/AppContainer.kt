package com.example.buscador_vuelos

import android.content.Context
import com.example.buscador_vuelos.data.FlightDatabase
import com.example.buscador_vuelos.data.FlightRepository
import com.example.buscador_vuelos.data.OfflineFlightRepository


interface AppContainer {
    val flightRepository: FlightRepository
}


class AppDataContainer (
    private val context: Context
) : AppContainer {


    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }
}