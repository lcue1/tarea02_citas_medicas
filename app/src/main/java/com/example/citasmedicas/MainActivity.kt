package com.example.citasmedicas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.citasmedicas.databinding.ActivityMainBinding
import com.example.citasmedicas.ui.ExitDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initAtributes()

        handleButtonsListener()
    }

    private fun handleButtonsListener() {//handle click buttons
        binding.exitApp.setOnClickListener { ExitDialog.showExitDialog(this) }//exit program dialog

    }

    private fun initAtributes() {//initialize atributes
        binding=ActivityMainBinding.inflate(layoutInflater)//config binding function
        setContentView(binding.root)
    }
}