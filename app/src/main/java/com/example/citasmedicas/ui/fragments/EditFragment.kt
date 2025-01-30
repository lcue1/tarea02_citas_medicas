package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.citasmedicas.R
import com.example.citasmedicas.databinding.FragmentEditBinding
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding

class EditFragment : Fragment() {

    private var userName: String? = null
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        userName = arguments?.getString("userName")

        return binding.root
    }


}