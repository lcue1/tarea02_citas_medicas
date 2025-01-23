package com.example.citasmedicas.ui.fragments

import android.content.Intent
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
import com.example.citasmedicas.data.appointments.Appointment
import com.example.citasmedicas.data.doctor.Doctor
import com.example.citasmedicas.data.doctor.DoctorDao
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding
import com.example.citasmedicas.ui.user.UserActivity
import com.example.citasmedicas.ui.user.Utils
import com.example.citasmedicas.viewModel.AppointmentsViewModel
import com.example.citasmedicas.viewModel.DoctorViewModel
import com.example.citasmedicas.viewModel.UserViewModel


class MedicalAppointmentFragment : Fragment() {
    //atributes
    private var userName:String? = null
    private var _binding: FragmentMedicalAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var database:AppDatabase
    private lateinit var appointmentViewModel:AppointmentsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista utilizando View Binding
        _binding = FragmentMedicalAppointmentBinding.inflate(inflater, container, false)
         userName = arguments?.getString("userName")

        userViewModel = UserViewModel()
        database = AppDatabase.getDatabase(requireContext())//data base
        appointmentViewModel= AppointmentsViewModel()
        setUserUI()

        // Configurar el listener del botón utilizando View Binding
      clickListeners()


        // Retornar la vista inflada por View Binding
        return binding.root
    }


    private fun setUserUI() {
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
        val appointmentViewModel = AppointmentsViewModel()
        appointmentViewModel.getAllAppointments(
            database = database,
            doSometing = {appointments->
                Log.d("appointments",appointments.toString())
            }
        )
    }

    private fun setPacientUI(user: User) {

    }

    private fun clickListeners() {//handle listeners click buttons
        binding.scheduleBtn.setOnClickListener {//go to schedule appointment and send userName
            insrtAppintment()

            Utils.sendUserNameToAnotherFragment(
                currentFragment = this,
                userName = userName!!,
                targetFragment = R.id.action_medicalAppointmentFragment_to_scheduleAppointmentFragment
            )
        }
    }
    //this function will be used in schedule fragment no escalable
    private fun insrtAppintment() {
        //get user info
        userViewModel.selectUserByUserName(// ask view model for an user
            name=userName.toString(),
            database = database,
            doSometing = {user->
                getUedicalList(user)
            }
        )
    }

    private fun getUedicalList(user: User) {
        val doctorViewModel = DoctorViewModel()
        doctorViewModel.getAllDoctors(
            database=database,
            doSometing = {doctors->
                if (doctors.size==0){
                    Log.d("userDoctors","no hay medicos disponibles")
                }else{
                    addMedicalAppointment(user,doctors)
                }
            }
        )
    }

    private fun addMedicalAppointment(user: User, doctors: List<Doctor>) {
        Log.d("userDoctors",user.toString())
        val appointmentViewModel = AppointmentsViewModel()
        val appointment=Appointment(
            usuarioId = user.id,
            medicoId = 1,
            fecha = "fecha",
            hora = "hora",
            estado = "estado"
        )
        appointmentViewModel.inserAppointment(
            appointment =appointment,
            database=database,
            onSuccess = {
                Log.d("appointment","success")
            },
            onError = {e->
                Log.d("appointment",e.toString())

            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Evitar pérdidas de memoria
        _binding = null
    }


}

