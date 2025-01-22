package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.citasmedicas.R
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding


class ScheduleAppointmentFragment : Fragment() {

    //atributes
    private var userName:String? = null
    private var _binding: FragmentMedicalAppointmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layou
        _binding = FragmentMedicalAppointmentBinding.inflate(inflater, container, false)
        userName = arguments?.getString("userName")
        Log.d("userName",userName.toString())

        binding.scheduleBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("userName", userName) // Reemplaza "John Doe" con el valor dinámico
            }

            findNavController().navigate(
                R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment,
                bundle
            )
        }
        return binding.root
    }

}