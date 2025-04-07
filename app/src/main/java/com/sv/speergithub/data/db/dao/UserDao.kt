package com.sv.speergithub.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sv.speergithub.data.db.entities.CachedUserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM cached_users WHERE username = :username")
    suspend fun getCachedUser(username: String): CachedUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: CachedUserEntity)

    @Query("DELETE FROM cached_users WHERE timestamp < :threshold")
    suspend fun clearStaleUsers(threshold: Long)
}