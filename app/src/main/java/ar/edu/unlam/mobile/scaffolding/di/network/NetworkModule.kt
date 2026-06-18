package ar.edu.unlam.mobile.scaffolding.di.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.DraftDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.models.interfaces.TuiterApiService
import ar.edu.unlam.mobile.scaffolding.data.repositories.implementation.LoginRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.implementation.PostRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.implementation.RegisterRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.LoginRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.interfaces.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://tuiter.fragua.com.ar/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTuiterApiServiceInstance(retrofit: Retrofit): TuiterApiService {

        return retrofit.create(TuiterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepositoryInstance(tuiterApiService: TuiterApiService): LoginRepository {

        return LoginRepositoryImpl(tuiterApiService)
    }

    @Provides
    @Singleton
    fun provideRegisterRepositoryInstance(tuiterApiService: TuiterApiService): RegisterRepository {

        return RegisterRepositoryImpl(tuiterApiService)
    }

    @Provides
    @Singleton
    fun providePostRepositoryInstance(
        tuiterApiService: TuiterApiService,
        tokenManager: TokenManager,
        draftDao: DraftDao
    ): PostRepository {

        return PostRepositoryImpl(tuiterApiService, tokenManager, draftDao)
    }
}