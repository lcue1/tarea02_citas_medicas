package com.example.citasmedicas.data.appointments

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.citasmedicas.data.doctor.Doctor


@Dao
interface AppointmentsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserAppointment(appointment: Appointment): Long


    @Query("SELECT * FROM Appointments ORDER BY fecha ASC, medicoId ASC")
    suspend fun getAllAppointments(): List<Appointment>

    @Query("SELECT * FROM Appointments WHERE usuarioId = :usuarioId")
    suspend fun getById(usuarioId: Int): List<Appointment>

}