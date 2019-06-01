package com.example.iproz.mycreateapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iproz.mycreateapp.model.EventModel
import kotlinx.android.synthetic.main.calendar_card_view.view.*
import java.util.ArrayList

class CalendarAdapter(
    val classdata: ArrayList<EventModel>,
    val context: Context
)

    : RecyclerView.Adapter<EventViewHolder>() {

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bookUser.text = classdata[position].bookUser
        holder.date.text = classdata[position].date
        holder.describe.text = classdata[position].describe
        holder.timeStart.text = classdata[position].timeStart
        holder.timeEnd.text = classdata[position].timeEnd

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.calendar_card_view, parent, false)

        return EventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return classdata.size
    }

}

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //ตาม layout
    var timeStart = itemView.tv_cardTimeStart!!
    var timeEnd = itemView.tv_cardTimeEnd!!
    var date = itemView.tv_cardDate!!
    var describe = itemView.tv_cardDetail!!
    var bookUser = itemView.tv_cardUser!!
}