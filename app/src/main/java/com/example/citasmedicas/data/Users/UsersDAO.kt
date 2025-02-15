package com.example.citasmedicas.data.Users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM  Users WHERE name = :name")
    fun getUserByUserName(name:String):User

    @Query("SELECT * FROM  Users WHERE id = :id")
    fun getUserByUserId(id:Int):User


    @Query("SELECT * FROM  Users WHERE type = :userType")
    fun getUserByUserType(userType:String):List<User>



}

