package com.example.lloydsassignment.di

import android.content.Context
import com.example.lloydsassignment.data.remote.ApiService
import com.example.lloydsassignment.core.Constants
import com.example.lloydsassignment.core.NetworkConnectivityChecker
import com.example.lloydsassignment.data.remote.repository.NewsRepository
import com.example.lloydsassignment.domain.repository.NewsRepositoryImpl
import com.example.lloydsassignment.domain.usecases.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(apiService: ApiService): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }

    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityChecker(@ApplicationContext context: Context): NetworkConnectivityChecker {
        return NetworkConnectivityChecker(context)
    }
}
