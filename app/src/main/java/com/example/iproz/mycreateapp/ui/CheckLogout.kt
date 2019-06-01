package com.example.iproz.mycreateapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.iproz.mycreateapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_check_logout.*

class CheckLogout : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_logout)

        mAuth = FirebaseAuth.getInstance()

        btn_cancel.setOnClickListener {
            finish()
        }

        btn_logout.setOnClickListener {

            mAuth.signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}
