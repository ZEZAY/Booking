package com.example.iproz.mycreateapp.model

data class UserModel(
    val name:String,
    val email:String
)

object StoreUser {
    var userModel:UserModel? = null
}