package com.example.iproz.mycreateapp.model

data class CreateClass (
    var className: String,
    var passRoom:String,
    var subjectImg:Int? = null,
    var comment:String
)

object StoreClass {
    val listClass = arrayListOf<CreateClass>()
}