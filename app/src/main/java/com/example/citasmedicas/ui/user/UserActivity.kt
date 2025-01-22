package com.example.citasmedicas.ui.user

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.citasmedicas.R

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)

        val userName=intent.getStringExtra("userName")
        Log.d("userName",userName.toString())

        // Obtén el NavHostFragment y el NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configura el gráfico de navegación con los argumentos
        val navGraph = navController.navInflater.inflate(R.navigation.pacient_graph)
        val bundle = Bundle().apply {
            putString("userName", userName)
        }
        navGraph.setStartDestination(R.id.medicalAppointmentFragment) // Asegúrate de usar el fragment correcto
        navController.setGraph(navGraph, bundle) // Configura el gráfico

    }
}