package com.github.iobeer.brewbot.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.jetbrains.exposed.sql.`java-time`.timestamp
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

object TemperatureRead : Table("temperature_reads") {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val timestamp: Column<Instant> = timestamp("timestamp")
    val temperature: Column<Double> = double("temperature")
}

suspend fun CoroutineScope.saveTemperatureToDB(channel: ReceiveChannel<Double>) {

    val dbConn = Database.connect("jdbc:mariadb://localhost:3306/brewbot", driver = "org.mariadb.jdbc.Driver",
            user = "brewbot", password = "iobeer")

    for (msg in channel) {
        transaction {
            addLogger(StdOutSqlLogger)
            TemperatureRead.insert {
                it[timestamp] = CurrentTimestamp()
                it[temperature] = msg
            }
        }
    }
}