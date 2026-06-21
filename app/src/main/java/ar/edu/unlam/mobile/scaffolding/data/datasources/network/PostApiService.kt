package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import retrofit2.http.GET
import retrofit2.http.Header

interface PostApiService {

    @GET("/api/v1/me/feed")
    suspend fun getFeed(
        @Header("Application-Token") applicationToken: String,
        @Header("Authorization") authorization: String,
    ): List<PostDto>
}
