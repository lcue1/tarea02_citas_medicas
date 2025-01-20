package com.example.citasmedicas

import android.content.Intent
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
import com.example.citasmedicas.ui.Pacient.PacientActivity
import com.example.citasmedicas.utils.Validations
import com.example.citasmedicas.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedUserType: String
    private  lateinit var database:AppDatabase
    private lateinit var userModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initAtributes()
        handleButtonsListener()
    }


    private fun handleButtonsListener() {//handle click buttons
        binding.exitApp.setOnClickListener { ExitDialog.showExitDialog(this) }//exit program dialog
        binding.loginBtn.setOnClickListener {createOrInitUser()}
    }

    private fun createOrInitUser() {
        var validation = Validations.validateEditTexts(arrayListOf(binding.userName))// validate edit ttext

        if (validation) {// edit text not empty
            selectedUserType = binding.typeUserSpiner.selectedItem.toString()//set actual value spiner selected
            val newUser = User(//reate an user with fields data
                name = binding.userName.text.toString().trim(),
                type = selectedUserType
            )
            userModel.insertUser(newUser, database,
                onSuccess = {//user created
                    defineUserType()
                },
                onError = { errorMessage ->//user exist
                    defineUserType()
                }
            )

        }
    }

    private fun defineUserType() {// open paciente ui or Admin ui
        userModel.selectUserByUserName(// ask view model for an user
            name=binding.userName.text.toString(),
            database = database,
            doSometing = {user->
                if(user.type=="Paciente"){
                Log.d("user",user.toString())
                    val intent = Intent(this, PacientActivity::class.java)
                    intent.putExtra("name",user.name)
                startActivity(intent)
                }else if(user.type=="Admin"){
                    Log.d("user",user.toString())

                }
            }
        )
    }


    private fun initAtributes() {//initialize atributes
        binding = ActivityMainBinding.inflate(layoutInflater)//config binding function
        setContentView(binding.root)

        //spiner
        var userTypes = arrayListOf("Paciente", "Admin")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            userTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeUserSpiner.adapter = adapter
        selectedUserType = "Paciente"

        database = AppDatabase.getDatabase(this)//data base
        userModel=UserViewModel()// view model user
    }


}