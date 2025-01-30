package com.example.citasmedicas.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.citasmedicas.R
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.appointments.Appointment
import com.example.citasmedicas.data.doctor.Doctor
import com.example.citasmedicas.databinding.FragmentMedicalAppointmentBinding
import com.example.citasmedicas.databinding.FragmentScheduleAppointmentBinding
import com.example.citasmedicas.ui.user.Utils
import com.example.citasmedicas.utils.Validations
import com.example.citasmedicas.viewModel.AppointmentsViewModel
import com.example.citasmedicas.viewModel.DoctorViewModel
import com.example.citasmedicas.viewModel.UserViewModel

class ScheduleAppointmentFragment : Fragment() {

    //atributes
    private var userName:String? = null
    private var _binding: FragmentScheduleAppointmentBinding? = null
    private val binding get() = _binding!!
    //spiner option selected
    var pasientSelected =""
    var doctorSelected =""
    var userType ="Admin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleAppointmentBinding.inflate(inflater, container, false)



        userName = arguments?.getString("userName")
        Log.d("scheduleuser",userName.toString())


        clickListener()
           loadUI()
        return binding.root
    }
/*
 */
    private fun loadUI() {
        val database = AppDatabase.getDatabase(requireContext())//get database instance
        val doctorViewModel=DoctorViewModel()
        doctorViewModel.getAllDoctors(
            database = database,
            doSometing = {doctors->
                Log.d("doctorssss",doctors.toString())
                if(doctors.size==0){// ther is not doctors
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "No hay doctores", Toast.LENGTH_SHORT).show()

                        Utils.sendUserNameToAnotherFragment(//go to medicalAppointmentFragment
                            currentFragment = this,
                            userName = userName!!,
                            targetFragment = R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment
                        )

                    }
                }else{
                    initTiemSpiner()
                    var userViewModel=UserViewModel()
                    userViewModel.selectUserByUserName(// ask view model for an user
                        name=userName.toString(),
                        database = database,
                        doSometing = {user->
                            if (user.type == "Admin") {
                                userViewModel.selectAllUsersByType(//get all pacients
                                    userType="Paciente",
                                    database = database,
                                    doSometing = {pacients->
                                        setAdminUI(pacients, doctors)
                                    }

                                )
                            //
                            }else if (user.type == "Paciente") {
                                //pendiente
                                userType="Pasiente"// pare el boton de agregar como paciente
                                setPacientUI(user,doctors)
                            }
                        }
                    )
                }
            }
        )
    }

    private fun initTiemSpiner() {
        val times = arrayListOf<String>()
        for(hour in 8..17){
            times.add("${hour}:00")
        }
        val adapter2 =  ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            times
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timeAppointmentSpiner.adapter = adapter2


    }

    private fun setAdminUI(pacients:List<User>, doctors: List<Doctor>) {

        //load pacients in spiner
        val pacientItems = pacients.map {  "nombre: ${it.name} id: ${it.id}"}
        val adapter =  ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            pacientItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.pacientIdSpiner.adapter = adapter

        //load doctros in spiner
        val doctorItems = doctors.map { "Dr : ${it.name}, Especialidad : ${it.specialty} id: ${it.id}" }
        val adapter2 =  ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            doctorItems
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.doctorIdSpiner.adapter = adapter2

        doctorSelected = binding.doctorIdSpiner.selectedItem.toString()


    }


    private fun setPacientUI(user: User, doctors:List<Doctor>) {
        requireActivity().runOnUiThread {
            binding.pacientIdSpiner.visibility=View.GONE//hide spiner admin function
            binding.titleSelectPacient.text="Paciente : ${user.name} id: ${user.id}"//change title
        }
        //load doctros in spiner
        val doctorItems = doctors.map { "Dr : ${it.name}, Especialidad : ${it.specialty} id: ${it.id}" }
        val adapter2 =  ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            doctorItems
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.doctorIdSpiner.adapter = adapter2

        doctorSelected = binding.doctorIdSpiner.selectedItem.toString()

    }

    private fun clickListener() {
        binding.appointmentBtn.setOnClickListener {

            Utils.sendUserNameToAnotherFragment(//go to medicalAppointmentFragment
                currentFragment = this,
                userName = userName!!,
                targetFragment = R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment
            )
        }//return to appointment fragment

        binding.addPpointment.setOnClickListener {
            //update
            val validacion = Validations.validateEditTexts(arrayListOf(binding.stateAppointment))
            if (validacion==false){
                return@setOnClickListener
            }
            if (userType=="Admin"){
                pasientSelected = binding.pacientIdSpiner.selectedItem.toString()
                doctorSelected = binding.doctorIdSpiner.selectedItem.toString()
                val dateAppointment = "${binding.dateAppointment.dayOfMonth}:" +
                        "${binding.dateAppointment.month}:" +
                        "${binding.dateAppointment.year}"
                val time = binding.timeAppointmentSpiner.selectedItem.toString()
                Log.d("infotoadd",pasientSelected.last().toString())
                Log.d("infotoadd",doctorSelected.last().toString())
                Log.d("infotoadd",dateAppointment)
                Log.d("infotoadd",time)

                val database =AppDatabase.getDatabase(requireContext())
                val appoinmentViewModel = AppointmentsViewModel()
                val appointment =Appointment(
                    usuarioId = pasientSelected.last().digitToInt(),
                    medicoId = doctorSelected.last().digitToInt(),
                    fecha = dateAppointment,
                    hora = time,
                    estado = binding.stateAppointment.text.toString()
                )

                Log.d("infotoadd",appointment.toString())

                appoinmentViewModel.inserAppointment(
                    appointment = appointment,
                    database = database,
                    onSuccess = {// message to uset indicate succes appointment and reutnr to medicalAppointment fragment
                        Log.d("errorrrr","agregado")
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Cita agregada exitosamente", Toast.LENGTH_SHORT)
                                .show()

                            Utils.sendUserNameToAnotherFragment(//go to medicalAppointmentFragment
                                currentFragment = this,
                                userName = userName!!,
                                targetFragment = R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment
                            )
                        }

                    },
                    onError = {error ->
                        Log.d("errorrrr",error.toString())

                    }
                )


            }else{
                pasientSelected = binding.titleSelectPacient.text.toString()
                doctorSelected = binding.doctorIdSpiner.selectedItem.toString()
                val dateAppointment = "${binding.dateAppointment.dayOfMonth}:" +
                        "${binding.dateAppointment.month}:" +
                        "${binding.dateAppointment.year}"
                val time = binding.timeAppointmentSpiner.selectedItem.toString()

                Log.d("infotoadd",pasientSelected.last().toString())
                Log.d("infotoadd",doctorSelected.last().toString())
                Log.d("infotoadd",dateAppointment)
                Log.d("infotoadd",time)
                val database =AppDatabase.getDatabase(requireContext())
                val appoinmentViewModel = AppointmentsViewModel()
                val appointment =Appointment(
                    usuarioId = pasientSelected.last().digitToInt(),
                    medicoId = doctorSelected.last().digitToInt(),
                    fecha = dateAppointment,
                    hora = time,
                    estado = binding.stateAppointment.text.toString()
                )

                Log.d("infotoadd",appointment.toString())

                appoinmentViewModel.inserAppointment(
                    appointment = appointment,
                    database = database,
                    onSuccess = {// message to uset indicate succes appointment and reutnr to medicalAppointment fragment
                        Log.d("errorrrr","agregado")
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Cita agregada exitosamente", Toast.LENGTH_SHORT)
                                .show()

                            Utils.sendUserNameToAnotherFragment(//go to medicalAppointmentFragment
                                currentFragment = this,
                                userName = userName!!,
                                targetFragment = R.id.action_scheduleAppointmentFragment_to_medicalAppointmentFragment
                            )
                        }

                    },
                    onError = {error ->
                        Log.d("errorrrr",error.toString())

                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Evitar pérdidas de memoria
        _binding = null
    }




}