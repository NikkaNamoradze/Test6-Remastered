package com.example.test6remastered.ui.login

import androidx.lifecycle.ViewModel
import com.example.test6remastered.datastore.DataStoreHandler
import com.example.test6remastered.model.UserInfoSend
import com.example.test6remastered.network.RetrofitInstance
import com.example.test6remastered.utils.ResponseHandler
import kotlinx.coroutines.flow.flow

class LoginViewModel : ViewModel() {

    fun getUserLogin(userInfoSend: UserInfoSend) = flow {
        emit(ResponseHandler.Loader(isLoading = true))

        val response = RetrofitInstance.authService().logIn(userInfoSend)

        if (response.isSuccessful && response.body() != null) {
            emit(ResponseHandler.Success(response.body()!!))
        } else {
            emit(ResponseHandler.Failure(response.errorBody()?.string() ?: "failure error"))
        }

        emit(ResponseHandler.Loader(isLoading = false))
    }

    suspend fun save(key: String, sessionValue: Boolean, tokenValue: String){
        DataStoreHandler.savePreference(key, sessionValue, tokenValue)
    }

    fun getPreferences() = DataStoreHandler.getPreference()

}