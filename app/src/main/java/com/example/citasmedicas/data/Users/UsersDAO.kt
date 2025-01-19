package com.example.citasmedicas.data.Users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long
}