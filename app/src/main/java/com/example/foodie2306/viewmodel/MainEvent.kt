package com.example.foodie2306.viewmodel

import com.example.domain.model.Product
import com.example.domain.model.ProductFromDb

sealed class MainEvent() {
    data class Upsert(val itemProduct: Product, val count: Int) : MainEvent()
    data class PlusCount(val itemProduct: ProductFromDb) : MainEvent()
    data class MinusCount(val itemProduct: ProductFromDb) : MainEvent()

    object ClearDb : MainEvent()
}