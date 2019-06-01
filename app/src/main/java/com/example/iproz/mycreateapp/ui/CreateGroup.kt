package com.example.iproz.mycreateapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.iproz.mycreateapp.R
import com.example.iproz.mycreateapp.model.CreateClass
import com.example.iproz.mycreateapp.model.StoreClass
import kotlinx.android.synthetic.main.activity_create_group.*

class CreateGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)


        btn_join.setOnClickListener {
            val groupName = et_GroupName.text.toString()
            val passRoom = et_joinCode_create.text.toString()

            val createClass = CreateClass(
                groupName,
                passRoom,
                comment = "")

            StoreClass.listClass.add(createClass)

            finish()
        }
    }
}