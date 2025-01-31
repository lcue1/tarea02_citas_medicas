package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.citasmedicas.R
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.appointments.Appointment
import com.example.citasmedicas.data.doctor.Doctor
import com.example.citasmedicas.databinding.FragmentEditBinding
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding
import com.example.citasmedicas.ui.user.Utils
import com.example.citasmedicas.viewModel.AppointmentsViewModel
import com.example.citasmedicas.viewModel.DoctorViewModel
import com.example.citasmedicas.viewModel.UserViewModel

class EditFragment : Fragment() {

    private var appointmentId: String? = null
    private var userName: String? = null
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: AppDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        appointmentId = arguments?.getString("userName")
        appointmentId?.let {
            Log.d("idApppointment",appointmentId.toString())
           database=AppDatabase.getDatabase(requireContext())
            getAppointmentInfo()
            clickListeners()
        }

        return binding.root
    }

    private fun getAppointmentInfo() {
        val appointmentViewModel = AppointmentsViewModel()
        appointmentViewModel.getByAppointmentId(
            appointmentId = appointmentId!!.toInt(),
            database=database,
            doSometing = {appointment->
                getUserInfo(appointment)
            }
        )
    }

    private fun getUserInfo(appointment: Appointment) {
        val userViewModel = UserViewModel()
        userViewModel.selectUserById(
            id = appointment.usuarioId,
            database = database,
            doSometing = {user->
                loadDoctors(user,appointment)
                userName=user.name
            }
        )

    }

    private fun loadDoctors(user: User,appointment: Appointment) {
        val doctorsViewModel = DoctorViewModel()
        doctorsViewModel.getAllDoctors(
            database = database,
            doSometing = {doctors->
                loadUserUI(user, appointment,doctors)
            }
        )
    }

    private fun loadUserUI(user: User,appointment: Appointment, doctors:List<Doctor>) {
        binding.idUser.text= "id cita ${user.id.toString()}"
        val actualDoctor = doctors.find { appointment.medicoId==it.id }
        actualDoctor?.let {
            binding.actualDoctor.text= "Doctor: ${actualDoctor.name}"
        }
        binding.stateAppointment.setText(appointment.estado)
        //load spiner
        val doctorItems = doctors.map { "Dr : ${it.name}, Especialidad : ${it.specialty} id: ${it.id}" }
        val adapter2 =  ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            doctorItems
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.doctorSpiner.adapter = adapter2
        //Hour spiner
        val hours = arrayListOf<String>()
        for (hour in 8..17){
            hours.add("${hour}:00")
        }
        val spinerHour =  ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            hours
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.hourSpiner.adapter = spinerHour

        val date = appointment.fecha.split(":")
        val day =date[0].toInt()
        val month=date[1].toInt()
        val year=date[2].toInt()
        Log.d("dateeeee",date.toString())
        binding.dateAppointment.updateDate(year,month,day)

    }

    private fun clickListeners() {
        binding.goMedicalAppointmentBtn.setOnClickListener {
            Utils.sendUserNameToAnotherFragment(
                currentFragment = this,
                userName = userName!!,
                targetFragment = R.id.action_editFragment_to_medicalAppointmentFragment
            )
        }
        binding.editAppointmentBtn.setOnClickListener {
            getInformationFromView()
        }
    }

    private fun getInformationFromView() {
        //usuarioId:Int, medicoId:Int,  fecha:String,hora:String, estado:String

        val appointmentViewModel = AppointmentsViewModel()
        val fecha ="${binding.dateAppointment.dayOfMonth}:${binding.dateAppointment.month}:${binding.dateAppointment.year}"
        val userId = binding.idUser.text.toString().last().toString()
        val hour = binding.hourSpiner.selectedItem.toString()
        val doctorId=binding.doctorSpiner.selectedItem.toString().last().toString()
        val stateAppointment = binding.stateAppointment.text.toString()

        appointmentViewModel.updateAppointment(
            usuarioId =userId.toInt(),
            hora = hour,
            fecha = fecha,
            database = database,
            medicoId = doctorId.toInt(),
            estado =  stateAppointment,
            doSometing = {result->
                Log.d("result",result.toString())

            }
        )




    }


}