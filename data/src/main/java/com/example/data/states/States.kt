package com.example.data.states

import com.example.domain.model.Product

sealed class States {
    data class Success(val list: List<Product>) : States()
    data class Error(val error: String?) : States()
    data object Loading : States()
}