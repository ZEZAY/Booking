package com.example.iproz.mycreateapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.iproz.mycreateapp.Adapter
import com.example.iproz.mycreateapp.R
import com.example.iproz.mycreateapp.callback.ClickRoomListenter
import com.example.iproz.mycreateapp.model.CreateClass
import com.example.iproz.mycreateapp.model.RoomModel
import com.example.iproz.mycreateapp.model.StoreClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout_default.*

class MainActivity : AppCompatActivity(), ClickRoomListenter {

    private val database = FirebaseFirestore.getInstance()
    val rooms = arrayListOf<RoomModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set hide title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        loadRoomData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_room,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.join_menu){
            startActivity(Intent(this, Join::class.java))
        }else if (item?.itemId == R.id.logout_menu){
            startActivity(Intent(this, CheckLogout::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    fun setRecyclerView() {
        rooms.forEach {roomModel->
            val room = CreateClass(
                className = roomModel.name,
                passRoom = roomModel.code,
                comment = roomModel.describe
            )

            StoreClass.listClass.add(room)
        }

        val adapter = Adapter(StoreClass.listClass, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun loadRoomData(){
        database.collection("Room").get()
            .addOnSuccessListener {
                val docs = it.documents
                for (doc in docs) {

                    val code:String = doc.get("Code") as String
                    val describe = doc.get("Describe") as String
                    val name = doc.get("Name") as String

                    val roomModel = RoomModel(
                        code,
                        describe,
                        name
                    )

                    rooms.add(roomModel)
                }

                setRecyclerView()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
    }

    override fun onClickedItem(code: String) {
        startActivity(calendarActivity(code))
    }
}
