package com.github.iobeer.brewbot.reader.temperature

import com.pi4j.component.temperature.TemperatureSensor
import com.pi4j.io.w1.W1Master
import com.pi4j.temperature.TemperatureScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.produce

fun CoroutineScope.readTemperatureSensor() = produce<Double>{
    val w1Master = W1Master()
    val tempSensor = w1Master.getDevices(TemperatureSensor::class.java)[0]

    while(true) {
        send(tempSensor.getTemperature(TemperatureScale.CELSIUS))
        Thread.sleep(2000)
    }
}