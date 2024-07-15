package com.example.data


import com.example.domain.model.Category
import com.example.domain.model.Product


    val sampleCategories = listOf(
        Category(1, "Category 1"),
        Category(2, "Category 2")
    )

    val sampleTags = listOf(
        Category(1, "Tag 1"),
        Category(2, "Tag 2")
    )

    val sampleProducts = listOf(
        Product(110.00, 1, "Description", 100.00, 90.00, 10, "photo_product_small.png", 1, "g", "Суши с гребешком", 2200, 3000, 50.00, listOf(2)),
        Product(210.00, 2, "Description", 150.00, 140.00, 11, "photo_product_small.png", 1, "g", "Суши с икрой лосося", 2200, 3000, 50.00, listOf())
    )

