package com.example.citasmedicas.data.appointments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.doctor.Doctor

@Entity(
    tableName = "Appointments",
    foreignKeys = [
        ForeignKey(
            entity = User::class, // Relación con la tabla User
            parentColumns = ["id"], // Columna en User
            childColumns = ["usuarioId"], // Columna en esta tabla
            onDelete = ForeignKey.CASCADE // Acción al borrar un usuario
        ),
        ForeignKey(
            entity = Doctor::class, // Relación con la tabla Doctor
            parentColumns = ["id"], // Columna en Doctor
            childColumns = ["medicoId"], // Columna en esta tabla
            onDelete = ForeignKey.CASCADE // Acción al borrar un médico
        )
    ]
)
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val medicoId: Int,
    val fecha: String,
    val hora: String,
    val estado: String
)