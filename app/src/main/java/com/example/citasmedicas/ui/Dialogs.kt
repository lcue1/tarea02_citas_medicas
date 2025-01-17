package com.example.citasmedicas.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context


object ExitDialog {
    fun showExitDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("¿Estás seguro de que quieres salir?")
            .setCancelable(false)
            .setPositiveButton("Sí") { _, _ ->
                // Aquí puedes agregar lo que pasa cuando el usuario elige salir.
                (context as? Activity)?.finish() // Finalizar la actividad
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Solo cierra el diálogo
            }

        val alert = builder.create()
        alert.show()
    }
}