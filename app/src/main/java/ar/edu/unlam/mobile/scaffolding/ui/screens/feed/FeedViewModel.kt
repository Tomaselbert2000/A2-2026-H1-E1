package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostResponse
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.PostRepository
import ar.edu.unlam.mobile.scaffolding.ui.constant.text_constant.TextConstant.UNKNOWN_ERROR_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {

        loadPosts()
    }

    fun loadPosts() {

        viewModelScope.launch {

            _uiState.value = FeedUiState.Loading

            try {

                val postList = postRepository.getPostList()

                _uiState.value = FeedUiState.Success(postList)

            } catch (exception: Exception) {

                val responseMessage = exception.message ?: UNKNOWN_ERROR_MESSAGE

                _uiState.value = FeedUiState.Error(responseMessage)
            }
        }
    }

    fun reloadPostList() {

        _uiState.value = FeedUiState.Loading

        loadPosts()
    }
}

sealed interface FeedUiState {

    data object Loading : FeedUiState
    data class Success(val posts: List<PostResponse>) : FeedUiState
    data class Error(val message: String) : FeedUiState
}