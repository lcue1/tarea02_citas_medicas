package com.example.citasmedicas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    // Insertar usuario en la base de datos
    fun insertUser(newUser: User, database: AppDatabase, onSuccess: () -> Unit, onError: (String) -> Unit) {
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


    fun selectUserByUserName(name: String, database: AppDatabase, doSometing:(user:User)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = database.userDao().getUserByUserName(name)
            doSometing(user)

        }
    }
    fun selectUserById(id: Int, database: AppDatabase, doSometing:(user:User)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = database.userDao().getUserByUserId(id)
            doSometing(user)

        }
    }
    fun selectAllUsersByType( userType:String,database: AppDatabase, doSometing:(users:List<User>)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = database.userDao().getUserByUserType(userType)
            doSometing(users)

        }
    }
}