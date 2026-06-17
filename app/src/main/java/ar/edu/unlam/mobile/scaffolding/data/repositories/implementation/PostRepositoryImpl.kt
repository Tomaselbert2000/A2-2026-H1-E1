package ar.edu.unlam.mobile.scaffolding.data.repositories.implementation

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.interfaces.TuiterApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationResponse
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.PostRepository
import jakarta.inject.Inject

class PostRepositoryImpl @Inject constructor(private val tuiterApiService: TuiterApiService) :
    PostRepository {

    override suspend fun createNewPost(
        createPostRequest: PostCreationRequest,
        userToken: String
    ): PostCreationResponse {

        return tuiterApiService.createPost(createPostRequest, userToken)
    }
}