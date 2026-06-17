package ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationResponse

interface PostRepository {

    suspend fun createNewPost(
        createPostRequest: PostCreationRequest,
        userToken: String
    ): PostCreationResponse
}