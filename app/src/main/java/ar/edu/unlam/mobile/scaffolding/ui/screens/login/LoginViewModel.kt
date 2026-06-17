package ar.edu.unlam.mobile.scaffolding.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.LoginRepository
import ar.edu.unlam.mobile.scaffolding.ui.constant.text_constant.TextConstant.UNKNOWN_ERROR_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) :
    ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {

            _uiState.value = LoginUiState.Loading

            try {

                val emailString = email.value
                val passwordString = password.value

                val loginRequest = LoginRequest(emailString, passwordString)

                val response = loginRepository.login(loginRequest)

                _uiState.value = LoginUiState.Success(response.token)

                tokenManager.saveToken(response.token)

            } catch (exception: Exception) {

                val responseMessage = exception.message ?: UNKNOWN_ERROR_MESSAGE

                _uiState.value = LoginUiState.Error(responseMessage)
            }
        }
    }

    fun restoreStatus() {

        _uiState.value = LoginUiState.Idle
    }

    fun updateEmailState(newEmailState: String) {

        _email.value = newEmailState
    }

    fun updatePasswordState(newPasswordState: String) {

        _password.value = newPasswordState
    }
}

sealed interface LoginUiState {

    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val responseToken: String) : LoginUiState
    data class Error(val errorMessage: String) : LoginUiState
}