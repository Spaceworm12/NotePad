package com.example.homework.util

sealed class Resource<out T> {
    data class Data<T>(val data: T) : Resource<T>()
    data class Error(val error: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    companion object {
        val Success: Resource<Unit> = Data(Unit)
    }
}
