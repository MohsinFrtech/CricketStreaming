package com.dream.live.cricket.score.hd.streaming.emulator.sensor

import android.content.Context
import com.dream.live.cricket.score.hd.streaming.emulator.DeviceState
import com.dream.live.cricket.score.hd.streaming.emulator.EmulatorDetector
import com.dream.live.cricket.score.hd.streaming.emulator.VerdictSource

internal class SensorEmulatorDetector(
    context: Context,
    sensorType: Int,
    private val eventsCount: Int
) : EmulatorDetector() {

    private val sensorDataProcessor = SensorDataValidator()
    private val sensorEventProducer = SensorEventProducer(context, sensorType)

    override suspend fun getState(): DeviceState {
        val sensorEvents = sensorEventProducer.getSensorEvents(eventsCount)
            .onFailure { return DeviceState.NotEmulator }
            .getOrThrow()

        return if (sensorDataProcessor.isEmulator(sensorEvents)) {
            DeviceState.Emulator(VerdictSource.Sensors(sensorEvents))
        } else {
            DeviceState.NotEmulator
        }
    }
}