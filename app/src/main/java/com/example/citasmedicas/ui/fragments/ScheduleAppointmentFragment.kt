package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.citasmedicas.R


class ScheduleAppointmentFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_schedule_appointment, container, false)
        val appointmentBtn = root.findViewById<Button>(R.id.appointmentBtn)
        appointmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment)
        }
        return root
    }

}