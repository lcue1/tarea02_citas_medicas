package com.example.citasmedicas.data.doctor

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "Doctors"
)
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val name:String,
    val specialty:String


)

