package com.sv.speergithub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sv.speergithub.data.db.dao.UserDao
import com.sv.speergithub.data.db.entities.CachedUserEntity

@Database(
    entities = [CachedUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}