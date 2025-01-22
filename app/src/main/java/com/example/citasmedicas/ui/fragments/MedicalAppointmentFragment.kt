package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.citasmedicas.R
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding
import com.example.citasmedicas.ui.user.Utils
import com.example.citasmedicas.viewModel.UserViewModel


class MedicalAppointmentFragment : Fragment() {
    //atributes
    private var userName:String? = null
    private var _binding: FragmentMedicalAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var database:AppDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista utilizando View Binding
        _binding = FragmentMedicalAppointmentBinding.inflate(inflater, container, false)
         userName = arguments?.getString("userName")

        userViewModel = UserViewModel()
        database = AppDatabase.getDatabase(requireContext())//data base

        getUserType()

        // Configurar el listener del botón utilizando View Binding
      clickListeners()

        // Retornar la vista inflada por View Binding
        return binding.root
    }

    private fun getUserType() {
        Log.d("User",userName.toString())

        userViewModel.selectUserByUserName(// ask view model for an user
            name=userName.toString(),
            database = database,
            doSometing = {user->
                if (user.type=="Admin"){
                    binding.userTitle.text = "${user.type} : ${user.name}"
                    setAdminUI(user)
                }else if (user.type=="Paciente"){
                    binding.userTitle.text = "${user.type} : ${user.name}"
                    setPacientUI(user)
                }
            }
        )
    }

    private fun setAdminUI(user: User) {

    }

    private fun setPacientUI(user: User) {

    }

    private fun clickListeners() {//handle listeners click buttons
        binding.scheduleBtn.setOnClickListener {//go to schedule appointment and send userName
            Utils.sendUserNameToAnotherFragment(
                currentFragment = this,
                userName = userName!!,
                targetFragment = R.id.action_medicalAppointmentFragment_to_scheduleAppointmentFragment
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Evitar pérdidas de memoria
        _binding = null
    }


}

