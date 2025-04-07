package com.sv.speergithub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sv.speergithub.data.repository.GithubRepository
import com.sv.speergithub.domain.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    fun loadUser(username: String) {
        _state.value = ProfileUiState.Loading

        viewModelScope.launch {
            try {
                val user = repository.getUser(username)
                _state.value = ProfileUiState.Success(user)
            } catch (e: Exception) {
                _state.value = ProfileUiState.Error("User not found")
            }
        }
    }

    sealed interface ProfileUiState {
        data object Idle : ProfileUiState
        data object Loading : ProfileUiState
        data class Success(val user: GithubUser) : ProfileUiState
        data class Error(val message: String) : ProfileUiState
    }
}
