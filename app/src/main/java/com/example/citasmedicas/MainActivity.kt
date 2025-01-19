package com.example.citasmedicas

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.databinding.ActivityMainBinding
import com.example.citasmedicas.ui.ExitDialog
import com.example.citasmedicas.utils.Validations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var selectedUserType:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initAtributes()

        handleButtonsListener()
    }

    private fun handleButtonsListener() {//handle click buttons
        binding.exitApp.setOnClickListener { ExitDialog.showExitDialog(this) }//exit program dialog
        binding.loginBtn.setOnClickListener {
          createOrInitUser()
        }
    }

    private fun createOrInitUser() {
        var validation = Validations.validateEditTexts(arrayListOf(binding.userName))// validate edit ttext

        if(validation){// edit text not empty
            selectedUserType = binding.typeUserSpiner.selectedItem.toString()//set actual value spiner selected
            val newUser = User(//reate an user with fields data
                name = binding.userName.text.toString().trim(),
                type = selectedUserType
            )
            val userDao = AppDatabase.getDatabase(this).userDao() // instancia de la base de datos y
            CoroutineScope(Dispatchers.IO).launch {
                try {// Create a new user
                    val insert = userDao.insertUser(newUser)

                } catch (e: SQLiteConstraintException) {// user exit, go to user UI

                }
            }
        }
    }

    private fun initAtributes() {//initialize atributes
        binding=ActivityMainBinding.inflate(layoutInflater)//config binding function
        setContentView(binding.root)

        //spiner
        var userTypes= arrayListOf("Pasiente","Admin")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            userTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeUserSpiner.adapter = adapter
        selectedUserType="Pasiente"

    }


}