package com.sv.speergithub.domain

import com.squareup.moshi.Json

data class GithubUser(
    val login: String,
    val id: Int,
    @Json(name = "avatar_url") val avatarUrl: String,
    val name: String?,
    val bio: String?,
    @Json(name = "followers") val followersCount: Int,
    @Json(name = "following") val followingCount: Int
)

data class GithubUserSimple(
    val login: String,
    val id: Int,
    @Json(name = "avatar_url") val avatarUrl: String
)
