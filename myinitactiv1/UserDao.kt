package com.example.myinitactiv1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
    @Query("DELETE FROM users")
    suspend fun delAllUsers()
    @Query("SELECT * FROM users WHERE (name = :username OR email = :username) AND password = :password LIMIT 1")
    fun getUserByUsernameAndPassword(username: String, password: String): User?
}
