package com.example.iproz.mycreateapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import com.example.iproz.mycreateapp.R
import com.example.iproz.mycreateapp.model.BookModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.toolbar_layout_default.*
import java.text.SimpleDateFormat
import java.util.*

fun Context.bookActivity(code: String): Intent {
    return Intent(this, Booking::class.java).apply {
        this.putExtra("code", code)
    }
}

class Booking : AppCompatActivity() {

    private val database = FirebaseFirestore.getInstance()
    private var mViewLoading: CatLoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        mViewLoading = CatLoadingView()
        mViewLoading?.setCanceledOnTouchOutside(false)


        val code = intent?.getStringExtra("code")
        et_roomCode.setText(code)

        val bookUser = FirebaseAuth.getInstance().currentUser?.email ?: ""
        et_bookUser.setText(bookUser)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        Toast.makeText(this, "HelloWorld", Toast.LENGTH_LONG).show()

        val calendar = Calendar.getInstance()


        et_date.setOnClickListener {

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    et_date.text = ("$mDay/$mMonth/$mYear")
                }, year, month, day
            )

            dpd.show()

        }

        et_starting.setOnClickListener {

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    val format = SimpleDateFormat("HH:mm").format(calendar.time)
                    et_starting.text = format
                }, hour, minute, true
            )

            tpd.show()
        }

        et_ending.setOnClickListener {

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    val format = SimpleDateFormat("HH:mm").format(calendar.time)
                    et_ending.text = format
                }, hour, minute, true
            )

            tpd.show()
        }

        buttonSaveOnClick()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_back_page, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.back_menu) {
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

    fun setShowMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun saveBooking(bookModel: BookModel) {
        mViewLoading?.show(supportFragmentManager, "")

        val colRef = database.collection("Book").document()
        colRef.set(bookModel)
            .addOnSuccessListener {
                mViewLoading?.dismiss()
                setShowMessage("Success")
                finish()

            }
            .addOnFailureListener {
                setShowMessage(it.message.toString())
                mViewLoading?.dismiss()
            }
    }

    fun buttonSaveOnClick() {
        btn_save.setOnClickListener {
            val bookModel = BookModel(
                et_bookUser.text.toString(),
                et_roomCode.text.toString(),
                et_detail.text.toString(),
                et_date.text.toString(),
                et_starting.text.toString(),
                et_ending.text.toString()
            )

            saveBooking(bookModel)
        }
    }


}