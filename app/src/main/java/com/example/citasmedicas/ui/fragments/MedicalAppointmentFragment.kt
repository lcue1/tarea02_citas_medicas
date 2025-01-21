package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.citasmedicas.R

class MedicalAppointmentFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        val root = inflater.inflate(R.layout.fragment_medical_appointment, container, false)
        val scheduleBtn = root.findViewById<Button>(R.id.scheduleBtn)
        scheduleBtn.setOnClickListener {
            findNavController().navigate(R.id.action_medicalAppointmentFragment_to_scheduleAppointmentFragment)
        }
        return root
    }

}