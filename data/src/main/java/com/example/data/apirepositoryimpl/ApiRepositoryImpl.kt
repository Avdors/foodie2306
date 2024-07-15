package com.example.data.apirepositoryimpl

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.repository.ApiRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class ApiRepositoryImpl(
private val client: HttpClient
): ApiRepository {
    override suspend fun getProducts(): List<Product> {
        val productList: List<Product> =client.get{
        url("https://anika1d.github.io/WorkTestServer/Products.json")
        }.body()
        return productList
    }

    override suspend fun getCategories(): List<Category> {
        val categorytList: List<Category> =client.get{
            url("https://anika1d.github.io/WorkTestServer/Categories.json")
        }.body()
        return categorytList
    }

    override suspend fun getTags(): List<Category> {
        val tags: List<Category> =client.get{
            url("https://anika1d.github.io/WorkTestServer/Tags.json")
        }.body()
        return tags
    }
}