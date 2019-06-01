package com.example.iproz.mycreateapp.model

data class EventModel(
    var bookUser:String,
    var code: String,
    var date: String,
    var describe: String,
    var timeStart: String,
    var timeEnd: String
)


object StoreEvent {
    val listEvent = arrayListOf<EventModel>()
}