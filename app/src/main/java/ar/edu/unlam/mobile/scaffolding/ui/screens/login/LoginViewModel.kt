package ar.edu.unlam.mobile.scaffolding.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    var email = MutableStateFlow("")
    var password = MutableStateFlow("")
    var uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)

    fun login() {
        viewModelScope.launch {

            uiState.value = LoginUiState.Loading

            try {

                val emailString = email.value
                val passwordString = password.value

                val loginRequest = LoginRequest(emailString, passwordString)

                val response = loginRepository.login(loginRequest)

                uiState.value = LoginUiState.Success(response.token)

            } catch (exception: Exception) {

                val responseMessage = exception.message ?: "Error desconocido"

                uiState.value = LoginUiState.Error(responseMessage)
            }
        }
    }

    fun restoreStatus() {

        uiState.value = LoginUiState.Idle
    }
}

sealed interface LoginUiState {

    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val responseToken: String) : LoginUiState
    data class Error(val errorMessage: String) : LoginUiState
}