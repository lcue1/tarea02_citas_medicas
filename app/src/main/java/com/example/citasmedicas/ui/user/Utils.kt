package com.example.citasmedicas.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citasmedicas.R

object Utils {
    fun sendUserNameToAnotherFragment(
        currentFragment: Fragment,
        userName:String,
        targetFragment:Int
    ) {
            val bundle = Bundle().apply {
                putString("userName", userName) // Puedes agregar más datos si es necesario
            }

            // Navegar al siguiente fragmento con el bundle
            currentFragment.findNavController().navigate(targetFragment, bundle)
        }
    }


