package ar.edu.unlam.mobile.scaffolding.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_MEDIUM

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val emailState by viewModel.email.collectAsState()
    val passwordState by viewModel.password.collectAsState()
    val restoreState = { viewModel.restoreStatus() }

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
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Bienvenido a Tuiter UNLaM",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(PADDING_MEDIUM)
        )

        OutlinedTextField(
            email,
            onValueChange = { newEmail -> onEmailChange(newEmail) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM),
            label = { Text("Correo electrónico") }
        )

        OutlinedTextField(
            password,
            onValueChange = { newPassword -> onPasswordChange(newPassword) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM),
            label = { Text("Contraseña") }
        )

        Button(
            onClick = onLoginButtonClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM)
        ) {

            Text("Iniciar sesión")
        }

        Text(
            "¿No eres usuario aún?"
        )

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM)
        ) {

            Text("Registrarse")
        }
    }
}

@Composable
private fun ShowLoadingStatusOnScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text("Cargando...", modifier = Modifier.padding(PADDING_MEDIUM))
    }
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Ocurrió un error al intentar iniciar sesión")
        Text("Código de estado: $errorMessage", modifier = Modifier.padding(PADDING_MEDIUM))

        Button(
            onClick = restoreState, colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Reintentar")
        }
    }
}