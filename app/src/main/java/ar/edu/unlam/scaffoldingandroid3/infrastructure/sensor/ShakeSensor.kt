package ar.edu.unlam.scaffoldingandroid3.infrastructure.sensor

interface ShakeSensor {
    fun start(onShake: () -> Unit)

    fun stop()
}
