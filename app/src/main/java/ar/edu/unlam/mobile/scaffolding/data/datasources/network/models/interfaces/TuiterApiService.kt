package ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.interfaces

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.post.PostCreationResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.register.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TuiterApiService {

    @POST("v1/login")
    @Headers("Application-Token: f6b67b050d16483ee05ce7563a5f8f246a85ea4eec3cde1064a3bc82ddddd921")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("v1/users")
    @Headers("Application-Token: f6b67b050d16483ee05ce7563a5f8f246a85ea4eec3cde1064a3bc82ddddd921")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("v1/me/tuits")
    @Headers("Application-Token: f6b67b050d16483ee05ce7563a5f8f246a85ea4eec3cde1064a3bc82ddddd921")
    suspend fun createPost(@Body request: PostCreationRequest, @Header("Authorization") userToken: String): PostCreationResponse
}