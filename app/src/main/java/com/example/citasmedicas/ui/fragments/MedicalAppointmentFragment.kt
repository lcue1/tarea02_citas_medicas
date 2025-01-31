package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citasmedicas.R
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.doctor.Doctor
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding
import com.example.citasmedicas.ui.adapters.AppointmentAdapter
import com.example.citasmedicas.ui.adapters.AppointmentItem
import com.example.citasmedicas.ui.user.Utils
import com.example.citasmedicas.viewModel.AppointmentsViewModel
import com.example.citasmedicas.viewModel.DoctorViewModel
import com.example.citasmedicas.viewModel.UserViewModel

class MedicalAppointmentFragment : Fragment() {
    // Atributos
    private var userName: String? = null
    private var _binding: FragmentMedicalAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var database: AppDatabase
    private lateinit var appointmentViewModel: AppointmentsViewModel
    private lateinit var doctorViewModel: DoctorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista utilizando View Binding
        _binding = FragmentMedicalAppointmentBinding.inflate(inflater, container, false)
        userName = arguments?.getString("userName")

        userViewModel = UserViewModel()
        database = AppDatabase.getDatabase(requireContext())  // Base de datos
        appointmentViewModel = AppointmentsViewModel()
        doctorViewModel = DoctorViewModel()

        // Configuración de la UI del usuario
        setUserUI()

        // Configurar el listener del botón
        clickListeners()

        return binding.root
    }

    private fun setUserUI() {
        Log.d("User", userName.toString())

        // Obtener el usuario por su nombre de usuario
        userViewModel.selectUserByUserName(
            name = userName.toString(),
            database = database
        ) { user ->
            binding.userTitle.text = "${user.type} : ${user.name}"
            if (user.type == "Admin") {
                setAdminUI()
            } else if (user.type == "Paciente") {
                setPacientUI(user)
            }
        }
    }

    private fun setAdminUI() {
        appointmentViewModel.getAllAppointments(
            database = database
        ) { appointments ->
            val appointmentItems = appointments.map {
                AppointmentItem(
                    idUserName = it.id.toString(),
                    dateAppointment = it.fecha,
                    hourAppointment = it.hora,
                    doctorName = it.medicoId.toString(),
                    specialityDoctor = "" // Asumiendo que no es necesario cargar el especialidad para el Admin
                )
            }
            updateRecyclerView(appointmentItems)
        }
    }

    private fun setPacientUI(user: User) {
        appointmentViewModel.getAppointmentByUserId(
            id = user.id,
            database = database
        ) { appointments ->
            val appointmentItems = mutableListOf<AppointmentItem>()
            var processedAppointments = 0  // Contador de citas procesadas

            appointments.forEach { appointment ->
                doctorViewModel.selectDoctorById(
                    id = appointment.medicoId,
                    database = database
                ) { doctor ->
                    val appointmentItem = AppointmentItem(
                        idUserName = appointment.id.toString(),
                        dateAppointment = appointment.fecha,
                        hourAppointment = appointment.hora,
                        doctorName = doctor.name,
                        specialityDoctor = doctor.specialty
                    )
                    appointmentItems.add(appointmentItem)
                    processedAppointments++

                    if (processedAppointments == appointments.size) {
                        updateRecyclerView(appointmentItems)
                    }
                }
            }
        }
    }

    private fun updateRecyclerView(appointmentItems: List<AppointmentItem>) {
        val appointmentAdapter = AppointmentAdapter(
            appointments = appointmentItems,
            updateAppointment = {idAppointment->
                Utils.sendUserNameToAnotherFragment(
                    currentFragment = this,
                    userName = idAppointment,
                    targetFragment = R.id.action_medicalAppointmentFragment_to_editFragment
                )
            }
        )
        requireActivity().runOnUiThread {
            val recyclerView: RecyclerView = binding.appointmentRecycler
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = appointmentAdapter
        }
    }

    private fun clickListeners() {
        binding.scheduleBtn.setOnClickListener {
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