package com.example.firebase_notification

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FirebaseDatabase.getInstance().getReference().child("Notifications").addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    id = snapshot.childrenCount.toInt()
                }
            }


        })

        FirebaseDatabase.getInstance().getReference().child("Notifications").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                if (txtname.text.toString().length == 0 && txtmessage.text.toString().length == 0){

                    Toast.makeText(applicationContext,"Plase fill all feilds",Toast.LENGTH_SHORT).show()
                }

                else{

                    mnotification()
                }

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }


        })


        button.setOnClickListener {

            if (txtname.text.toString().length == 0 && txtmessage.text.toString().length == 0){

                Toast.makeText(applicationContext,"Plase fill all feilds",Toast.LENGTH_SHORT).show()
            }
            else{

                val user = User(txtname.text.toString(), txtmessage.text.toString())

                FirebaseDatabase.getInstance().getReference().child("Notifications").child(id.toString()).setValue(user)
            }

        }


    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun mnotification() {

        val name = txtname.text.toString()
        val message = txtmessage.text.toString()


        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){

            val channel : NotificationChannel = NotificationChannel("n","n",NotificationManager.IMPORTANCE_DEFAULT)
            val notificationmanager : NotificationManager = getSystemService(NotificationManager::class.java)
            notificationmanager.createNotificationChannel(channel)
        }

        val notification : NotificationCompat.Builder = NotificationCompat.Builder(this,"n")
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setAutoCancel(true)
            .setContentText(name +" "+ message)
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notifymanager : NotificationManagerCompat = NotificationManagerCompat.from(this)
        notifymanager.notify(999,notification.build())


    }
}
