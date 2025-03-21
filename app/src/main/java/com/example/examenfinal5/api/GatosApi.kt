package com.example.examenfinal5.api

import retrofit2.Response
import retrofit2.http.GET

interface GatosApi {

    @GET("breeds")
    suspend fun getListadoGatos(): Response<List<Results>>
}