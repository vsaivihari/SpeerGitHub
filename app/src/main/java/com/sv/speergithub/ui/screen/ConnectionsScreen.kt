package com.sv.speergithub.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sv.speergithub.navigation.Screen
import com.sv.speergithub.ui.viewmodel.ConnectionsViewModel

@Composable
fun ConnectionsScreen(
    navController: NavController,
    username: String,
    type: String,
    viewModel: ConnectionsViewModel
) {
    val users by viewModel.state.collectAsState()
    val refreshing by viewModel.loading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(refreshing)

    LaunchedEffect(Unit) {
        viewModel.loadConnections(username, type)
    }

    Column(
        modifier = Modifier.padding(16.dp, top = 32.dp)
    ) {
        Text(
            text = if (type == "followers") "Followers" else "Following",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.loadConnections(username, type)
        }) {
            LazyColumn {
                items(users.size) { i ->
                    val user = users[i]
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.Profile.createRoute(user.login))
                            }
                            .padding(12.dp)
                    ) {
                        AsyncImage(
                            model = user.avatarUrl,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(user.id.toString())
                            Text("@${user.login}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }


}