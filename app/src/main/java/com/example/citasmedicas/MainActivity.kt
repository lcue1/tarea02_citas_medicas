package com.example.citasmedicas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.citasmedicas.data.AppDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.doctor.Doctor
import com.example.citasmedicas.databinding.ActivityMainBinding
import com.example.citasmedicas.ui.ExitDialog
import com.example.citasmedicas.ui.user.UserActivity
import com.example.citasmedicas.utils.Validations
import com.example.citasmedicas.viewModel.DoctorViewModel
import com.example.citasmedicas.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedUserType: String
    private  lateinit var database:AppDatabase
    private lateinit var userViewModel: UserViewModel
    private lateinit var doctorViewModel: DoctorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initAtributes()
        handleListener()
        doctorViewModel.getAllDoctors(
            database=database,
            doSometing = {doctors->
                Log.d("doctorsss",doctors.toString())
            }
        )
    }


    private fun handleListener() {//handle click buttons
        binding.exitApp.setOnClickListener { ExitDialog.showExitDialog(this) }//exit program dialog
        binding.loginBtn.setOnClickListener {determineActionAcordingUser()}

        binding.typeUserSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                // Realiza acciones basadas en el elemento seleccionado
                Log.d("Spinner", "Seleccionaste: $selectedItem")
                if (binding.typeUserSpiner.selectedItem.toString()=="Medico"){
                    binding.medicalSpecialty.visibility=View.VISIBLE// show  edit text
                }else{
                    binding.medicalSpecialty.visibility=View.GONE// hide edit text
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción opcional si no se selecciona nada
                Log.d("Spinner", "Nada seleccionado")
            }
        }

    }

    private fun determineActionAcordingUser() {
        val itemSelected = binding.typeUserSpiner.selectedItem.toString()//get selected from spiner
        if(itemSelected=="Medico"){
            createDoctor()
        }else{// Pcient or Sdin user
            initOrCreateUser()
        }
    }

    private fun createDoctor() {
        var validation = Validations.validateEditTexts(arrayListOf(binding.userName, binding.medicalSpecialty))// validate edit ttext
        if (validation) {// edit text not empty
            val doctor =Doctor(
                name = binding.userName.text.toString(),
                specialty = binding.medicalSpecialty.text.toString()
            )
            doctorViewModel.insertDoctor(
                newDoctor = doctor,
                database=database,
                onSuccess = {
                    Log.d("id","doctor created")
                    doctorViewModel.getAllDoctors(
                        database=database,
                        doSometing = {doctors->
                            Log.d("doctorsss",doctors.toString())
                        }
                        )
                },
                onError = {error->
                    Log.d("error", error)

                }
            )
        }
    }

    private fun initOrCreateUser(){

        var validation = Validations.validateEditTexts(arrayListOf(binding.userName))// validate edit ttext
        if (validation) {// edit text not empty
            selectedUserType = binding.typeUserSpiner.selectedItem.toString()//set actual value spiner selected
            val newUser = User(//reate an user with fields data
                name = binding.userName.text.toString().trim(),
                type = selectedUserType
            )
            userViewModel.insertUser(newUser, database,
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
        userViewModel.selectUserByUserName(// ask view model for an user
            name=binding.userName.text.toString(),
            database = database,
            doSometing = {user->
                Log.d("userrrr",user.toString())
                val intent = Intent(this, UserActivity::class.java)
                intent.putExtra("userName",user.name)
                startActivity(intent)

            }
        )
    }

    private fun initAtributes() {//initialize atributes
        binding = ActivityMainBinding.inflate(layoutInflater)//config binding function
        setContentView(binding.root)

        //spiner
        var userTypes = arrayListOf("Paciente", "Admin","Medico")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            userTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeUserSpiner.adapter = adapter
        selectedUserType = "Paciente"

        //init database and viwmodls
        database = AppDatabase.getDatabase(this)//data base
        userViewModel=UserViewModel()
        doctorViewModel=DoctorViewModel()
    }
}