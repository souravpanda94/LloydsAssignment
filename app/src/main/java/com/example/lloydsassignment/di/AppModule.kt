package com.example.lloydsassignment.di

import com.example.lloydsassignment.presentation.news_list.NewsViewModel
import com.example.lloydsassignment.data.remote.ApiService
import com.example.lloydsassignment.core.Constants
import com.example.lloydsassignment.core.NetworkConnectivityChecker
import com.example.lloydsassignment.data.remote.repository.NewsRepository
import com.example.lloydsassignment.domain.repository.NewsRepositoryImpl
import com.example.lloydsassignment.domain.usecases.NewsUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



val appModule = module {

    single {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    single<NewsRepository> {
        NewsRepositoryImpl(get())
    }

    factory { NewsUseCase(get()) }
    viewModel { NewsViewModel(get()) }

    single { NetworkConnectivityChecker(androidContext()) }
}