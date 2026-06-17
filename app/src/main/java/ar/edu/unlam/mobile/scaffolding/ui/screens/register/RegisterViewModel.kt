package ar.edu.unlam.mobile.scaffolding.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.register.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.RegisterRepository
import ar.edu.unlam.mobile.scaffolding.ui.constant.text_constant.TextConstant.BLANK_FIELDS_ERROR_MESSAGE
import ar.edu.unlam.mobile.scaffolding.ui.constant.text_constant.TextConstant.UNKNOWN_ERROR_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) :
    ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    var uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)


    fun register() {

        viewModelScope.launch {

            if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank()) {

                uiState.value = RegisterUiState.Error(BLANK_FIELDS_ERROR_MESSAGE)

                return@launch
            }

            uiState.value = RegisterUiState.Loading

            try {

                val nameString = name.value
                val emailString = email.value
                val passwordString = password.value

                val registerRequest = RegisterRequest(nameString, emailString, passwordString)

                val response = registerRepository.register(registerRequest)

                uiState.value = RegisterUiState.Success(response.token)

            } catch (exception: Exception) {

                val responseMessage = exception.message ?: UNKNOWN_ERROR_MESSAGE

                uiState.value = RegisterUiState.Error(responseMessage)
            }
        }
    }

    fun updateName(newName: String) {

        _name.value = newName
    }

    fun updateEmail(newEmail: String) {

        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {

        _password.value = newPassword
    }

    fun resetForm() {

        _name.value = ""
        _email.value = ""
        _password.value = ""
    }

    fun setUiStateAsIdle() {

        uiState.value = RegisterUiState.Idle
    }
}

sealed interface RegisterUiState {

    data object Idle : RegisterUiState
    data object Loading : RegisterUiState
    data class Success(val responseToken: String) : RegisterUiState
    data class Error(val errorMessage: String) : RegisterUiState
}