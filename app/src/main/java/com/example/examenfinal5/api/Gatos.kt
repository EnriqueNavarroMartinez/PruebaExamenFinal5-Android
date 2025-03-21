package com.example.examenfinal5.api

data class GatosResponse(
    val results: List<Results>
)

data class Results(
    val id:String,
    val name:String,
    val temperament:String,
    val origin:String,
    val description: String
)