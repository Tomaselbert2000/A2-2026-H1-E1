package ar.edu.unlam.mobile.scaffolding.data.repositories.implementation

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.interfaces.TuiterApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostResponse
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.PostRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class PostRepositoryImpl @Inject constructor(
    private val tuiterApiService: TuiterApiService,
    private val tokenManager: TokenManager
) :
    PostRepository {

    override suspend fun createNewPost(
        createPostRequest: PostCreationRequest,
        userToken: String
    ): PostCreationResponse {

        return tuiterApiService.createPost(createPostRequest, userToken)
    }

    override suspend fun getPostList(): List<PostResponse> {

        return tuiterApiService.getPosts(
            userToken = tokenManager.tokenFlow.first(),
            pageNumber = 1,
            onlyParents = true
        )
    }
}