package com.example.foodie2306.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.apirepositoryimpl.ApiRepositoryImpl
import com.example.data.database.MyDataBase
import com.example.data.dbrepositoryimpl.DbRepositoryImpl
import com.example.domain.repository.ApiRepository
import com.example.domain.repository.DbRepository
import com.example.domain.usecase.CategoryListUseCase
import com.example.domain.usecase.DbListUseCase
import com.example.domain.usecase.DeleteUseCase
import com.example.domain.usecase.GetTagsUseCase
import com.example.domain.usecase.ProductListUseCase
import com.example.domain.usecase.UpsertItemUseCase
import com.example.foodie2306.viewmodel.DbViewModel
import com.example.foodie2306.viewmodel.ProductsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single {
        MyDataBase.getDataBase(androidContext())
    }
    single {
        HttpClient(Android){
            install(ContentNegotiation){
                json()
            }
            install(Logging){
                level = LogLevel.ALL
            }
        }
    }

    single<ApiRepository> { ApiRepositoryImpl(get())}
    single<DbRepository> { DbRepositoryImpl(get()) }

    factory { ProductListUseCase(get()) }
    factory { CategoryListUseCase(get()) }
    factory { GetTagsUseCase(get()) }
    factory { UpsertItemUseCase(get()) }
    factory { DbListUseCase(get()) }
    factory { DeleteUseCase(get()) }

    viewModel {ProductsViewModel(get(), get(), get())}
    viewModel { DbViewModel(get(), get(), get(), get())  }

}