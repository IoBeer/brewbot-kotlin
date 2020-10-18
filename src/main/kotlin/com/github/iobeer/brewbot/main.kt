package com.github.iobeer.brewbot

import com.github.iobeer.brewbot.database.saveTemperatureToDB
import com.github.iobeer.brewbot.reader.temperature.readTemperatureSensor
import kotlinx.coroutines.*

fun main(args: Array<String>) {
    println("Iniciando Brewbot...")
    runBlocking {
        val tempReader = readTemperatureSensor()
        launch { saveTemperatureToDB(tempReader) }
    }
    println("Encerrando...")
}