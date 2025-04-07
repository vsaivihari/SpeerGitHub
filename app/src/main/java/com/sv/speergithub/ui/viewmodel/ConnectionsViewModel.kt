package com.sv.speergithub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sv.speergithub.data.repository.GithubRepository
import com.sv.speergithub.domain.GithubUser
import com.sv.speergithub.domain.GithubUserSimple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<GithubUserSimple>>(emptyList())
    val state: StateFlow<List<GithubUserSimple>> = _state.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun loadConnections(username: String, type: String) {
        viewModelScope.launch {
            _loading.value = true

            val result = try {
                when (type) {
                    "followers" -> repository.getFollowers(username)
                    "following" -> repository.getFollowing(username)
                    else -> emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }

            _state.value = result
            _loading.value = false
        }
    }

}