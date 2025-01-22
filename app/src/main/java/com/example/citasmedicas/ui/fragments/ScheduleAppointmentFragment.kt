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
import com.example.citasmedicas.ui.user.Utils


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
        clickListener()
        return binding.root
    }

    private fun clickListener() {
        binding.scheduleBtn.setOnClickListener {
            Utils.sendUserNameToAnotherFragment(
                currentFragment = this,
                userName = userName!!,
                targetFragment = R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Evitar pérdidas de memoria
        _binding = null
    }

}