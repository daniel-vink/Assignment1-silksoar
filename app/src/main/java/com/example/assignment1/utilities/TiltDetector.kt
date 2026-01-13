package com.example.assignment1.utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.assignment1.interfaces.TiltCallback
import javax.security.auth.callback.Callback
import kotlin.math.abs

class TiltDetector(context: Context, private val tiltCallback: TiltCallback) {

    private val sensorManager = context.getSystemService(
        Context.SENSOR_SERVICE) as SensorManager

    private val sensor = sensorManager.getDefaultSensor(
        Sensor.TYPE_ACCELEROMETER
    )

    private var timestamp: Long = 0L

    private lateinit var sensorEventListener: SensorEventListener

    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // Pass
                }
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val z = event.values[2]
                calculateTilt(x, z)
            }

        }
    }

    private fun calculateTilt(x: Float, z: Float) {
        if (System.currentTimeMillis() - timestamp >= 600) {
            timestamp = System.currentTimeMillis()
            if (x <= -4.0) {
                tiltCallback?.tiltRight()
            } else if (x >= 4.0) {
                timestamp = System.currentTimeMillis()
                tiltCallback?.tiltLeft()
            }

            if (z <= 5.0) {
                tiltCallback?.tiltBackward()
            } else if (z > 8.0) {
                timestamp = System.currentTimeMillis()
                tiltCallback?.tiltForward()
            }
        }
    }

    fun start() {
        sensorManager
            .registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
    }


    fun stop() {
        sensorManager
            .unregisterListener(
                sensorEventListener,
                sensor
            )
    }
}