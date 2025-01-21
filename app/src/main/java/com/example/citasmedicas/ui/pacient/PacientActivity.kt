package com.example.citasmedicas.ui.pacient

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.citasmedicas.R

class PacientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pacient)

        val userName =intent.getStringExtra("userName")
        Log.d("name",userName.toString())

    }
}