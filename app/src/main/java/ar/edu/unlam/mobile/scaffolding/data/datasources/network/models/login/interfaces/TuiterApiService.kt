package ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.interfaces

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TuiterApiService {

    @POST("v1/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse
}