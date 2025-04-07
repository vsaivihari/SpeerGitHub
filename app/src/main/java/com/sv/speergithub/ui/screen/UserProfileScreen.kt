package com.sv.speergithub.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.sv.speergithub.domain.GithubUser
import com.sv.speergithub.navigation.Screen
import com.sv.speergithub.ui.screen.components.ProfileSkeleton
import com.sv.speergithub.ui.viewmodel.UserViewModel

@Composable
fun UserProfileScreen(viewModel: UserViewModel,
                      username: String,
                      navController: NavController
) {

    LaunchedEffect (username) {
        viewModel.loadUser(username)
    }

    val state by viewModel.state.collectAsState()

    Column(Modifier.padding(16.dp, top = 32.dp)) {
        when (state) {
            is UserViewModel.ProfileUiState.Idle -> {
                Text("Search for a GitHub user...")
            }
            is UserViewModel.ProfileUiState.Loading -> {
                ProfileSkeleton()
            }
            is UserViewModel.ProfileUiState.Success -> {
                UserProfile((state as UserViewModel.ProfileUiState.Success).user, navController)
            }
            is UserViewModel.ProfileUiState.Error -> {
                Text("User not found.", color = MaterialTheme.colorScheme.error)
            }
        }
    }

}

@Composable
fun UserProfile(user: GithubUser, navController: NavController) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(user.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(user.name ?: user.login, style = MaterialTheme.typography.titleLarge)
            Text("@${user.login}", style = MaterialTheme.typography.bodyMedium)
            Text(user.bio ?: "", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "${user.followersCount} followers",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Connections.createRoute(user.login, "followers"))
                    }
                )
                Text(
                    "${user.followingCount} following",
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Connections.createRoute(user.login, "following"))
                    }
                )
            }
        }
    }
}



