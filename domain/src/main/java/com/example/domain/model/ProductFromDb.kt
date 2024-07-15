package com.example.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product")
class ProductFromDb (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "oldPrice")
    val oldPrice: Int?,
    @ColumnInfo(name = "count")
    val count: Int,
    @ColumnInfo(name = "image")
    val image: String
)