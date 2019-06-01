package com.example.iproz.mycreateapp.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.Toast
import com.example.iproz.mycreateapp.CalendarAdapter
import com.example.iproz.mycreateapp.R
import com.example.iproz.mycreateapp.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.toolbar_layout_default.*

fun Context.calendarActivity(code: String): Intent {
    return Intent(this, CalendarActivity::class.java).apply {
        this.putExtra(CODE, code)
    }
}

private const val CODE = "key_code"

class CalendarActivity : AppCompatActivity() {
    private val database = FirebaseFirestore.getInstance()

    val events = arrayListOf<EventModel>()

    var code: String? = null
    //Loading Animation
    private var mViewLoading: CatLoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //set icon back activity
        toolbar.setNavigationIcon(R.drawable.ic_return)

        setSupportActionBar(toolbar)

        //set hide title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //set back activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewLoading = CatLoadingView()
        mViewLoading?.setCanceledOnTouchOutside(true)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            getDateBooking(date)
        }

        bindBundle()
    }

    fun bindBundle() {
        code = intent?.getStringExtra(CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_booking, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when {
            item?.itemId == R.id.book_menu -> {
                startActivity(bookActivity(code ?: ""))
                true
            }
            item?.itemId == android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getDateBooking(date: String) {
        mViewLoading?.show(supportFragmentManager, "")
        val user = FirebaseAuth.getInstance().currentUser?.email ?: ""

        val database = FirebaseFirestore.getInstance()
        val docRef = database.collection("Book")
        val query = docRef.whereEqualTo("code", code)
        query.get().addOnSuccessListener {
            val docs = it.documents
            val events = arrayListOf<EventModel>()

            for (doc in docs) {
                val bookUser = doc.get("bookUser") as String
                val code = doc.get("code") as String
                val dateStr = doc.get("date") as String
                val describe = doc.get("detail") as String
                val timeStart = doc.get("timeStart") as String
                val timeEnd = doc.get("timeEnd") as String

                val queryWithDate = docRef.whereEqualTo("date", date)
                queryWithDate.get().addOnSuccessListener {
                    val eventModel = EventModel(
                        bookUser,
                        code,
                        dateStr,
                        describe,
                        timeStart,
                        timeEnd
                    )

                    events.add(eventModel)
                }
            }
            loadEvent()
            mViewLoading?.dismiss()
        }.addOnFailureListener {
            mViewLoading?.dismiss()
        }
    }

    fun setEventRecyclerView() {
        events.forEach { eventModel ->
            val event = EventModel(
                bookUser = eventModel.bookUser,
                code = eventModel.code,
                date = eventModel.date,
                describe = eventModel.describe,
                timeStart = eventModel.timeStart,
                timeEnd = eventModel.timeEnd
            )

            StoreEvent.listEvent.add(event)
        }

        val calAdapter = CalendarAdapter(StoreEvent.listEvent, this)
        calRecyclerView.layoutManager = LinearLayoutManager(this)
        calRecyclerView.adapter = calAdapter
    }

    fun loadEvent() {
        database.collection("Book").get()
            .addOnSuccessListener {
                val docs = it.documents
                for (doc in docs) {

                    val bookUser = doc.get("bookUser") as String
                    val code: String = doc.get("code") as String
                    val date = doc.get("date") as String
                    val detail = doc.get("detail") as String
                    val timeEnd = doc.get("timeEnd") as String
                    val timeStart = doc.get("timeStart") as String

                    val eventModel = EventModel(
                        bookUser,
                        code,
                        date,
                        detail,
                        timeEnd,
                        timeStart
                    )

                    events.add(eventModel)
                }

                setEventRecyclerView()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }


}
