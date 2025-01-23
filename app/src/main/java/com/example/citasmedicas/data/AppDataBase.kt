package com.example.citasmedicas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.citasmedicas.data.Users.User
import com.example.citasmedicas.data.Users.UserDao
import com.example.citasmedicas.data.appointments.Appointment
import com.example.citasmedicas.data.appointments.AppointmentsDao
import com.example.citasmedicas.data.doctor.Doctor
import com.example.citasmedicas.data.doctor.DoctorDao


@Database(entities = [User::class, Doctor::class, Appointment::class], version = 1, exportSchema = false)abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun doctorDao(): DoctorDao
    abstract fun appointmentsDao(): AppointmentsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                 //   .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}