package com.sv.speergithub.data.repository

import android.util.Log
import com.sv.speergithub.data.api.GithubApiService
import com.sv.speergithub.data.db.dao.UserDao
import com.sv.speergithub.data.db.entities.CachedUserEntity
import com.sv.speergithub.domain.GithubUser
import com.sv.speergithub.domain.GithubUserSimple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val api: GithubApiService,
    private val userDao: UserDao
) {

    // TTL = 10 minutes
    private val ttl = 10 * 60 * 1000L

    suspend fun getUser(username: String): GithubUser = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val cached = userDao.getCachedUser(username)

        if (cached != null && now - cached.timestamp < ttl) {
            return@withContext cached.toDomainModel()
        }

        // Fetch from API
        val user = api.getUser(username)
        Log.d("GithubRepository", "Fetched user: $user")

        // Cache it
        userDao.insertUser(user.toEntity(now))

        // Clean up expired entries
        userDao.clearStaleUsers(now - ttl)

        return@withContext user
    }

    suspend fun getFollowers(username: String): List<GithubUserSimple> = withContext(Dispatchers.IO) {
        Log.d("GithubRepository", "Fetching followers for $username")
        api.getFollowers(username)
    }

    suspend fun getFollowing(username: String): List<GithubUserSimple> = withContext(Dispatchers.IO) {
        api.getFollowing(username)
    }

    // Extension: GithubUser → CachedUserEntity
    private fun GithubUser.toEntity(timestamp: Long) = CachedUserEntity(
        username = login,
        name = name,
        bio = bio,
        avatarUrl = avatarUrl,
        followers = followersCount,
        following = followingCount,
        timestamp = timestamp,
        id = id
    )

    // Extension: CachedUserEntity → GithubUser
    private fun CachedUserEntity.toDomainModel() = GithubUser(
        login = username,
        name = name,
        bio = bio,
        avatarUrl = avatarUrl,
        followersCount = followers,
        followingCount = following,
        id = id
    )
}