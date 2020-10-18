package com.github.iobeer.brewbot.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager

suspend fun CoroutineScope.saveTemperatureToDB(channel: ReceiveChannel<Double>) {

    val dbConn = Database.connect("jdbc:mariadb://localhost:3306/brewbot", driver = "org.mariadb.jdbc.Driver",
    user = "brewbot", password = "iobeer")

    for(msg in channel) {
        transaction {
            addLogger(StdOutSqlLogger)
        }
        println("<<< Recebido: ${msg}")
    }
}