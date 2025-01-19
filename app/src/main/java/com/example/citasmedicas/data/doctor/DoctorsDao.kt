package com.example.citasmedicas.data.doctor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DoctorDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDoctor(doctor: Doctor): Long


    @Query("SELECT * FROM Doctors")
    suspend fun getAllDoctors(): List<Doctor>
}