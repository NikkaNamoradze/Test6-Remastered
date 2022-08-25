package com.example.test6remastered.utils

sealed class ResponseHandler {
    data class Success<T>(val result: T) : ResponseHandler()
    data class Failure(val error: String) : ResponseHandler()
    data class Loader(val isLoading: Boolean) : ResponseHandler()
}
