package com.example.citasmedicas.viewModel

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {

    // falta programar cambiar
    fun insertDoctor(newUser: User, database: AppDatabase, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = database.userDao().insertUser(newUser)
                if (result > 0) {
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error desconocido")
            }
        }
    }
/*
    private fun getAllDoctors() {
        val doctorDao = AppDatabase.getDatabase(this).doctorDao() // instancia de la base de datos y
        CoroutineScope(Dispatchers.IO).launch {
            try {// Create a new user
                val doctors = doctorDao.getAllDoctors()
                Log.d("doctors", doctors.toString())
            } catch (e: SQLiteConstraintException) {// user exit, go to user UI
                Log.d("doctors", e.toString())

            }
        }
    }

private fun inserDoctor() {
        val doctorDao = AppDatabase.getDatabase(this).doctorDao() // instancia de la base de datos y
        CoroutineScope(Dispatchers.IO).launch {
            val newDoctor = Doctor(
                name = "Julio Paredes",
                specialty = "Traumatologo"
            )
            try {// Create a new user
                val insert = doctorDao.insertDoctor(newDoctor)

            } catch (e: SQLiteConstraintException) {// user exit, go to user UI

            }
        }
    }

 */
}