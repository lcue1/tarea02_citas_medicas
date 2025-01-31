package com.example.citasmedicas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.appointments.Appointment
import com.example.citasmedicas.data.doctor.Doctor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AppointmentsViewModel : ViewModel() {

    // falta programar cambiar
    fun inserAppointment(appointment: Appointment, database: AppDatabase, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = database.appointmentsDao().inserAppointment(appointment)
                if (result > 0) {
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error desconocido")
            }
        }
    }

    fun getAllAppointments(database: AppDatabase, doSometing:(doctors:List<Appointment>)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val appointments = database.appointmentsDao().getAllAppointments()
            doSometing(appointments)
        }

    }

    fun getAppointmentByUserId(id: Int,database: AppDatabase, doSometing:(doctors:List<Appointment>)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val appointments = database.appointmentsDao().getById(id)
            doSometing(appointments)
        }
    }

    fun getByAppointmentId(appointmentId: Int,database: AppDatabase, doSometing:(Appointment)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val appointment = database.appointmentsDao().getByAppointmentId(appointmentId)
            doSometing(appointment)
        }
    }
    fun updateAppointment(usuarioId:Int, medicoId:Int,  fecha:String,hora:String, estado:String,database: AppDatabase, doSometing:(Int)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val appointment = database.appointmentsDao().updateAppointment(usuarioId, medicoId,  fecha,hora, estado)
            doSometing(appointment)
        }
    }
}