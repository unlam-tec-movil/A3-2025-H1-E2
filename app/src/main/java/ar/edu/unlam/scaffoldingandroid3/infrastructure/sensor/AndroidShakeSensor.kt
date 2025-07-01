package ar.edu.unlam.scaffoldingandroid3.infrastructure.sensor

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import kotlin.math.sqrt

class AndroidShakeSensor(
    private val context: Context,
) : ShakeSensor,
    SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var shakeListener: (() -> Unit)? = null
    private var lastShakeTime = 0L

    override fun start(onShake: () -> Unit) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        shakeListener = onShake

        sensorManager?.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI,
        )
    }

    override fun stop() {
        sensorManager?.unregisterListener(this)
        shakeListener = null
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt(x * x + y * y + z * z)
        val now = System.currentTimeMillis()
        // Aceleracion para ser activado y cooldown
        if (acceleration > 20f && now - lastShakeTime > 1500) {
            lastShakeTime = now
            vibrate()
            shakeListener?.invoke()
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int,
    ) {
        // No esta en uso pero pide incluirlo en la clase
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    @SuppressLint("ServiceCast")
    private fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    300, // duración en ms
                    VibrationEffect.DEFAULT_AMPLITUDE,
                ),
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(300)
        }
    }
}
