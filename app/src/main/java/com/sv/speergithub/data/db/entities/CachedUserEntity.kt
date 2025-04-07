package com.sv.speergithub.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_users")
data class CachedUserEntity(
    @PrimaryKey val username: String,
    val id: Int,
    val name: String?,
    val bio: String?,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val timestamp: Long
)
