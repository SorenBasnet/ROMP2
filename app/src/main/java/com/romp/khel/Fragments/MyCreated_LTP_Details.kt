package com.romp.khel.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.romp.khel.R
import com.romp.khel.keyy

var num:Int = 0
lateinit var buto:Button
lateinit var butoo:Button
lateinit var numbo:TextView

var database = FirebaseDatabase.getInstance().getReference()
var conditionref: DatabaseReference = database.child("lookingtoplay").child(keyy.toString())
lateinit var auth: FirebaseAuth


class MyCreated_LTP_Details : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_created_ltp_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numbo=view.findViewById(R.id.mcltpdetails_num)
        buto=view.findViewById(R.id.mcltpdetails_up)
        butoo=view.findViewById(R.id.mcltpdetails_down)

        var dum:String?
        var dumdum=0

        conditionref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"onCancelled called", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dum= dataSnapshot.child("playercount").value.toString()
                    dumdum= dum!!.toInt()
                numbo.text= dumdum.toString()
            }

        })



        buto.setOnClickListener {
            dumdum= dumdum!!.toInt()+1
            conditionref.child("playercount").setValue(dumdum)
        }
        butoo.setOnClickListener {
            dumdum= dumdum!!.toInt()-1
            conditionref.child("playercount").setValue(dumdum)
        }
    }
}