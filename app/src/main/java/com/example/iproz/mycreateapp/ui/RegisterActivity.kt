package com.example.iproz.mycreateapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.iproz.mycreateapp.R
import com.google.firebase.auth.FirebaseAuth
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar_layout_clear.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    //Loading Animation
    private var mViewLoading: CatLoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        mViewLoading = CatLoadingView()
        mViewLoading?.setCanceledOnTouchOutside(false)

        btn_signUp.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            createNewAccount(user, pass)
        }
    }

    private fun createNewAccount(email: String, password: String) {
        if (email.trim().isNotEmpty() && password.trim().isNotEmpty()) {
            mViewLoading?.show(supportFragmentManager, "Register Loading")
            mViewLoading?.setText("LOADING...")

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        mViewLoading?.dismiss()

                        val currentUser = auth.currentUser

                        Toast.makeText(this@RegisterActivity, "Create user success. User is $currentUser", Toast.LENGTH_LONG).show()
                        finish()

                    }else {
                        mViewLoading?.dismiss()
                        Toast.makeText(this@RegisterActivity, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }else {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show()
        }
    }
}


/*
class RegisterActivity : AppCompatActivity(), AuthenCallback {//เรียกใช้interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btn_signUp.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            val name = name.text.toString()
            val nick = nick.text.toString()
            val email = email.text.toString()
            val tel = tel.text.toString()


            if (username.isEmpty()) {
                Toast.makeText(this, "Enter your Username", Toast.LENGTH_LONG).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Enter your Password", Toast.LENGTH_LONG).show()
            } else if (name.isEmpty()) {
                Toast.makeText(this, "Enter your Name", Toast.LENGTH_LONG).show()
            }else if (nick.isEmpty()) {
                Toast.makeText(this, "Enter your Nick Name", Toast.LENGTH_LONG).show()
            }else if (email.isEmpty()) {
                Toast.makeText(this, "Enter your E-Mail", Toast.LENGTH_LONG).show()
            }else if (tel.isEmpty()) {
                Toast.makeText(this, "Enter your Phone Number", Toast.LENGTH_LONG).show()
            } else {
                val userModel = UserModel(
                    username,
                    password
                )

                //new instance
                val user = User(this, this)
                user.create(userModel)
            }


        }
    }


    override fun onLoginSuccess(model: UserModel) {

    }

    override fun onCreateSuccess() {
        finish()
    }

}
*/

