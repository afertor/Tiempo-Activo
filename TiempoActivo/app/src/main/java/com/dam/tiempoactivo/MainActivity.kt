package com.dam.tiempoactivo

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView



class MainActivity : AppCompatActivity() {
    private var startTime: Long = 0
    private var totalTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el tiempo total activo
        totalTime = savedInstanceState?.getLong("totalTime") ?: 0

        // Actualiza el TextView con el tiempo total activo
        updateActiveTimeText()

        // Registra el tiempo de inicio
        startTime = SystemClock.elapsedRealtime()
    }

    override fun onPause() {
        super.onPause()

        // Calcula el tiempo activo
        val endTime = SystemClock.elapsedRealtime()
        val activeTime = endTime - startTime

        // Registra el tiempo activo en Logcat
        Log.d("TiempoActivo", "Tiempo activo: $activeTime ms")

        // Actualiza el tiempo total activo
        totalTime += activeTime

        // Guarda el tiempo total activo en el Bundle para que se restaure en onResume
        val bundle = Bundle()
        bundle.putLong("totalTime", totalTime)
        onSaveInstanceState(bundle)
    }

    override fun onResume() {
        super.onResume()

        // Actualiza el tiempo total activo en el TextView
        updateActiveTimeText()

        // Registra el tiempo de inicio
        startTime = SystemClock.elapsedRealtime()
    }

    private fun updateActiveTimeText() {
        val seconds = totalTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
        tiempoActivoTextView.text = "Tiempo total activo: $formattedTime"
    }
}
