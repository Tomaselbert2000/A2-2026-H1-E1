package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.Draft
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationRequest
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.PostRepository
import ar.edu.unlam.mobile.scaffolding.ui.constant.text_constant.TextConstant.UNKNOWN_ERROR_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val tokenManager: TokenManager
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<PostCreationUiState>(PostCreationUiState.Idle)
    val uiState: StateFlow<PostCreationUiState> = _uiState.asStateFlow()

    private val _draftId: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    init {

        viewModelScope.launch {

            postRepository.getAllDrafts().collect {

                    drafts ->
                _draftId.value = drafts.lastOrNull()?.postId ?: 0
                _message.value = drafts.lastOrNull()?.postMessage ?: ""
            }
        }
    }

    fun createPost() {

        viewModelScope.launch {

            _uiState.value = PostCreationUiState.Loading

            try {

                val postMessage = message.value

                val newPostRequest = PostCreationRequest(postMessage)

                val response =
                    postRepository.createNewPost(newPostRequest, tokenManager.tokenFlow.first())

                _uiState.value = PostCreationUiState.Success(response.message)

                if (_draftId.value > 0) {

                    postRepository.deleteDraft(_draftId.value)
                }

            } catch (exception: Exception) {

                val responseErrorMessage = exception.message ?: UNKNOWN_ERROR_MESSAGE

                _uiState.value = PostCreationUiState.Error(responseErrorMessage)
            }
        }
    }

    fun createDraft(draftMessage: String) {

        viewModelScope.launch {

            val draft = Draft(postMessage = draftMessage)

            postRepository.saveDraft(draft)

            _uiState.value = PostCreationUiState.DraftSaved
        }
    }

    fun onMessageChange(newMessage: String) {

        _message.value = newMessage
    }

    fun restoreStatus() {

        _uiState.value = PostCreationUiState.Idle
    }
}

sealed interface PostCreationUiState {

    data object Idle : PostCreationUiState
    data object Loading : PostCreationUiState
    data object DraftSaved : PostCreationUiState
    data class Success(val responseMessage: String) : PostCreationUiState
    data class Error(val errorMessage: String) : PostCreationUiState
}