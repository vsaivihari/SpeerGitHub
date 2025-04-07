package com.sv.speergithub.data.api

import com.sv.speergithub.domain.GithubUser
import com.sv.speergithub.domain.GithubUserSimple
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): GithubUser

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<GithubUserSimple>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<GithubUserSimple>
}