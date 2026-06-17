package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.ui.components.shared.ShowLoadingStatusOnScreen
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_LARGE
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_MEDIUM
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_SMALL

private const val DRAFT_BUTTON_TEXT = "Borrador"
private const val POST_BUTTON_TEXT = "Publicar"
private const val TEXTFIELD_PROMPT_TEXT = "¿Qué estás pensando?..."

@Composable
fun PostCreationScreen(
    postCreationViewModel: PostCreationViewModel,
    onPostAction: () -> Unit,
    onCancelAction: () -> Unit,
) {

    val message by postCreationViewModel.message.collectAsState()
    val uiState by postCreationViewModel.uiState.collectAsState()
    val restoreState = { postCreationViewModel.restoreStatus() }

    when (val state = uiState) {

        is PostCreationUiState.Idle -> {

            ShowPostCreationForm(
                message,
                { newMessage -> postCreationViewModel.onMessageChange(newMessage) },
                { postCreationViewModel.createDraft() },
                { postCreationViewModel.createPost() },
                onCancelAction
            )
        }

        is PostCreationUiState.Loading -> {

            ShowLoadingStatusOnScreen()
        }

        is PostCreationUiState.Success -> {

            onPostAction()
        }

        is PostCreationUiState.Error -> {

            ShowErrorMessageOnScreen(
                restoreState,
                state.errorMessage
            )
        }
    }
}

@Composable
private fun ShowPostCreationForm(
    message: String,
    onPostMessageChangeAction: (String) -> Unit,
    onDraftAction: () -> Unit,
    onPostAction: () -> Unit,
    onCancelAction: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(PADDING_MEDIUM)
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Row(
            modifier = Modifier.padding(PADDING_MEDIUM)
        ) {

            IconButton(
                onClick = onCancelAction
            ) {

                Icon(Icons.Default.Cancel, contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onDraftAction,
                modifier = Modifier.padding(PADDING_SMALL),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            ) {

                Text(DRAFT_BUTTON_TEXT)
            }

            Button(
                onClick = onPostAction,
                modifier = Modifier.padding(PADDING_SMALL),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {

                Text(POST_BUTTON_TEXT)
            }
        }

        TextField(
            value = message,
            placeholder = { Text(TEXTFIELD_PROMPT_TEXT) },
            onValueChange = { newMessage -> onPostMessageChangeAction(newMessage) },
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING_LARGE),
            colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(PADDING_LARGE),
        )
    }
}

@Composable
fun ShowErrorMessageOnScreen(onRestoreStateAction: () -> Unit, errorMessage: String) {

    Row(modifier = Modifier.padding(PADDING_MEDIUM)) {

        IconButton(

            onClick = onRestoreStateAction
        ) {

            Icon(Icons.Default.ArrowCircleLeft, contentDescription = null)
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Ocurrió un error al procesar el post: $errorMessage",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(PADDING_MEDIUM)
        )
    }
}