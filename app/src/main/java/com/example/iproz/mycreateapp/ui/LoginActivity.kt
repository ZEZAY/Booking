package com.example.iproz.mycreateapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.iproz.mycreateapp.R
import com.example.iproz.mycreateapp.model.StoreUser
import com.example.iproz.mycreateapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    //Loading Animation
    private var mViewLoading: CatLoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        mViewLoading = CatLoadingView()
        mViewLoading?.setCanceledOnTouchOutside(false)

        btn_login.setOnClickListener {

            val user = username.text.toString()
            val pass = password.text.toString()

            signIn(user, pass)
        }

        btn_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        checkLogin()
    }

    private fun checkLogin() {
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn(user: String, pass: String) {
        if (user.trim().isNotEmpty() && pass.trim().isNotEmpty()) {
            mViewLoading?.show(supportFragmentManager, "")
            auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    mViewLoading?.dismiss()
                    val firebaseUser = auth.currentUser
                    updateUI(firebaseUser)
                }else {
                    mViewLoading?.dismiss()
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
        }else {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val name = user.displayName.toString()
            val email = user.email.toString()

            StoreUser.userModel = UserModel(
                name = name,
                email = email)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
