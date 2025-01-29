package com.example.citasmedicas.viewModel

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.doctor.Doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {

    // falta programar cambiar
    fun insertDoctor(newDoctor: Doctor, database: AppDatabase, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = database.doctorDao().insertDoctor(newDoctor)
                if (result > 0) {
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error desconocido")
            }
        }
    }

    fun getAllDoctors(database: AppDatabase, doSometing:(doctors:List<Doctor>)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val doctors = database.doctorDao().getAllDoctors()
            doSometing(doctors)
        }

    }
    fun selectDoctorById(id: Int, database: AppDatabase, doSometing:(doctor:Doctor)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val doctor = database.doctorDao().getDoctorById(id)
            doSometing(doctor)
        }
    }


}