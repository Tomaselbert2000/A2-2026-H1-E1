package ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.interfaces

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TuiterApiService {

    @POST("v1/login")
    @Headers("Application-Token: f6b67b050d16483ee05ce7563a5f8f246a85ea4eec3cde1064a3bc82ddddd921")
    suspend fun login(@Body request: LoginRequest) : LoginResponse
}