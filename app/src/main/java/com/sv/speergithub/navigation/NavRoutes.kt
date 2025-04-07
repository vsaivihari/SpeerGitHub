package com.sv.speergithub.navigation

sealed class Screen(val route: String) {
    object Profile : Screen("profile/{username}") {
        fun createRoute(username: String) = "profile/$username"
    }

    object Connections : Screen("connections/{username}/{type}") {
        fun createRoute(username: String, type: String) = "connections/$username/$type"
    }
}