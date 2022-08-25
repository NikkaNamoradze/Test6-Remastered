package com.example.test6remastered.network

import com.example.test6remastered.model.LoginResponse
import com.example.test6remastered.model.RegistrationResponse
import com.example.test6remastered.model.UserInfoSend
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("login")
    suspend fun logIn(@Body body: UserInfoSend): Response<LoginResponse>

    @POST("register")
    suspend fun registration(@Body body: UserInfoSend): Response<RegistrationResponse>
}