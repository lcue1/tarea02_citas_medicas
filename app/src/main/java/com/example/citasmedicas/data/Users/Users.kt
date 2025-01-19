package com.example.citasmedicas.data.Users

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Users",
    indices = [Index(value = ["name"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Campo ID único
    val name: String, // Nombre del usuario
    val type: String // Puede ser "Paciente" o "Admin"
)