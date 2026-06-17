package ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostResponse

interface PostRepository {

    suspend fun createNewPost(
        createPostRequest: PostCreationRequest,
        userToken: String
    ): PostCreationResponse

    suspend fun getPostList() : List<PostResponse>
}