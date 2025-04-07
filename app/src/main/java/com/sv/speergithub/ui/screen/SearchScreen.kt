package com.sv.speergithub.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sv.speergithub.navigation.Screen
import com.sv.speergithub.ui.viewmodel.UserViewModel


@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: UserViewModel
) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(end = 16.dp, start = 16.dp, top = 32.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("GitHub Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (query.isNotBlank()) {
                    navController.navigate(Screen.Profile.createRoute(query))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }
    }

    }