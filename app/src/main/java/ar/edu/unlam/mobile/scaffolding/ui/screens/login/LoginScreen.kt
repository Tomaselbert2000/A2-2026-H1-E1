package ar.edu.unlam.mobile.scaffolding.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_MEDIUM

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val emailState by viewModel.email.collectAsState()
    val passwordState by viewModel.password.collectAsState()
    val restoreState = { viewModel.restoreStatus() }

    Column() {

        when (val state = uiState) {

            is LoginUiState.Idle -> {
                ShowLoginForm(
                    emailState,
                    passwordState,
                    { newEmailState -> viewModel.email.value = newEmailState },
                    { newPasswordState -> viewModel.password.value = newPasswordState },
                    { viewModel.login() }
                )
            }

            is LoginUiState.Loading -> {
                ShowLoadingStatusOnScreen()
            }

            is LoginUiState.Success -> {
                ShowSuccessScreen(
                    state.responseToken
                )
            }

            is LoginUiState.Error -> {
                ShowErrorMessageOnScreen(
                    state.errorMessage,
                    restoreState
                )
            }
        }
    }
}


@Composable
private fun ShowLoginForm(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Text(
            "Correo electrónico",
            modifier = Modifier.padding(PADDING_MEDIUM),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        TextField(email, onValueChange = { newEmail -> onEmailChange(newEmail) })

        Text(
            "Contraseña",
            modifier = Modifier.padding(PADDING_MEDIUM),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextField(password, onValueChange = { newPassword -> onPasswordChange(newPassword) })

        Button(
            onClick = onLoginButtonClick,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            )
        ) {

            Text("Iniciar sesión")
        }
    }
}

@Composable
private fun ShowLoadingStatusOnScreen() {

    CircularProgressIndicator()
    Text("Cargando...", modifier = Modifier.padding(PADDING_MEDIUM))
}

@Composable
private fun ShowSuccessScreen(responseToken: String) {

    Text(
        "Inicio de sesión exitoso, token asignado:\n${responseToken}",
        modifier = Modifier.padding(PADDING_MEDIUM)
    )
}

@Composable
private fun ShowErrorMessageOnScreen(errorMessage: String, restoreState: () -> Unit) {

    Text(errorMessage, modifier = Modifier.padding(PADDING_MEDIUM))

    Button(onClick = restoreState) {
        Text("Reintentar")
    }
}