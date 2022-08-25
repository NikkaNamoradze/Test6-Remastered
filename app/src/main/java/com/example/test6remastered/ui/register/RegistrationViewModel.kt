package com.example.test6remastered.ui.register

import androidx.lifecycle.ViewModel
import com.example.test6remastered.model.RegistrationResponse
import com.example.test6remastered.model.UserInfoSend
import com.example.test6remastered.network.RetrofitInstance
import com.example.test6remastered.utils.ResponseHandler
import kotlinx.coroutines.flow.flow

class RegistrationViewModel : ViewModel() {

    fun getUserRegistrations(userInfoSend: UserInfoSend) = flow {
        emit(ResponseHandler.Loader(isLoading = true))
        val response = RetrofitInstance.authService().registration(userInfoSend)
        if (response.isSuccessful && response.body() != null) {
            emit(ResponseHandler.Success(response.body()!!))
        } else {
            emit(ResponseHandler.Failure(response.errorBody()?.string() ?: "Registration Error"))
        }
        emit(ResponseHandler.Loader(isLoading = false))
    }

}