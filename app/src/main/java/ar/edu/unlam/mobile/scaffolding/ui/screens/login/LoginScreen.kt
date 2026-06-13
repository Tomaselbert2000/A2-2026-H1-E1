package ar.edu.unlam.mobile.scaffolding.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.shared.TuiterButton
import ar.edu.unlam.mobile.scaffolding.ui.components.shared.TuiterOutlinedTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.shared.TuiterTextLabel
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_MEDIUM
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_SMALL

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSuccess: () -> Unit) {

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

            onLoginSuccess()
        }

        is LoginUiState.Error -> {
            ShowErrorMessageOnScreen(
                restoreState,
                state.errorMessage
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
        TuiterTextLabel(
            R.string.login_title_label,
            MaterialTheme.typography.headlineLarge,
            MaterialTheme.colorScheme.primary,
            Modifier.padding(PADDING_MEDIUM)
        )

        TuiterOutlinedTextField(
            email,
            { newEmail -> onEmailChange(newEmail) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM),
            R.string.email_label
        )

        TuiterOutlinedTextField(
            password,
            { newPassword -> onPasswordChange(newPassword) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM),
            R.string.password_label,
            PasswordVisualTransformation(),
            KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        TuiterButton(
            R.string.sign_in,
            onLoginButtonClick,
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM)
        )

        TuiterTextLabel(
            R.string.not_user_yet,
            MaterialTheme.typography.titleSmall,
            MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(PADDING_MEDIUM)
        )

        TuiterButton(
            R.string.sign_up,
            {},
            ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_MEDIUM)
        )
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
        TuiterTextLabel(
            R.string.login_loading_indicator_label,
            MaterialTheme.typography.titleLarge,
            MaterialTheme.colorScheme.onSurface,
            Modifier.padding(PADDING_MEDIUM)
        )
    }
}

@Composable
private fun ShowErrorMessageOnScreen(restoreState: () -> Unit, errorMessage: String) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(PADDING_SMALL)) {
            TuiterTextLabel(
                R.string.login_error_label,
                MaterialTheme.typography.titleMedium,
                MaterialTheme.colorScheme.error,
                Modifier.padding(PADDING_MEDIUM)
            )

            Text(
                errorMessage,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(PADDING_MEDIUM)
            )
        }

        TuiterTextLabel(
            R.string.login_error_status_code,
            MaterialTheme.typography.titleMedium,
            MaterialTheme.colorScheme.error,
            Modifier.padding(PADDING_MEDIUM)
        )

        TuiterButton(
            R.string.login_retry_label,
            restoreState,
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
        )
    }
}