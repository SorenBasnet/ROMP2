package com.romp.khel.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.romp.khel.R
import com.romp.khel.dataclass.LookingtoPlayRoom
import kotlinx.coroutines.NonCancellable.cancel
import java.util.*

class FormFragment_LTP : Fragment() {
    var database = FirebaseDatabase.getInstance().getReference()
    lateinit var auth: FirebaseAuth

    lateinit var selectvenuebutton:Button
    lateinit var venuename:TextView
    lateinit var venuelocation:TextView
    lateinit var timepicker:TimePicker
    lateinit var timepickerval:TextView
    lateinit var endtimepicker:TimePicker
    lateinit var endtimepickerval:TextView
    lateinit var datepick:DatePicker
    lateinit var datepickval:TextView
    lateinit var phone:EditText
    lateinit var contacttext:EditText
    lateinit var totalplayers:EditText
    lateinit var joinedplayers:EditText
    lateinit var pricepp:EditText
    lateinit var addinfo:EditText
    lateinit var submit:Button

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_ltp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        selectvenuebutton=view.findViewById(R.id.ltpform_selectvenuebutton)
        venuename=view.findViewById(R.id.ltpform_editvenue)
        venuelocation=view.findViewById(R.id.ltpform_editlocation)
        timepicker=view.findViewById(R.id.ltpform_timepicker)
        timepickerval=view.findViewById(R.id.ltpform_edittime)
        endtimepicker=view.findViewById(R.id.ltpform_endtimepicker)
        endtimepickerval=view.findViewById(R.id.ltpform_editendtime)
        datepick=view.findViewById(R.id.ltpform_datepicker)
        datepickval=view.findViewById(R.id.ltpform_editdate)
        phone=view.findViewById(R.id.ltpform_editPhone)
        contacttext=view.findViewById(R.id.ltpform_editcontact)
        totalplayers=view.findViewById(R.id.ltpform_edittotalplayers)
        joinedplayers=view.findViewById(R.id.ltpform_editjoinedplayers)
        pricepp=view.findViewById(R.id.ltpform_editpricepp)
        addinfo=view.findViewById(R.id.ltpform_editaddinfo)
        submit=view.findViewById(R.id.button2)
        val today = Calendar.getInstance()


        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Select Venue")
        val day = arrayOf("Kick Futsal Lalitpur","Shankhamul Futsal", "Royal Futsal","Shantinagar Futsal", "Prismatic Futsal and Recreation Center", "Dhobighat Futsal", "Maa Banglamukhi Futsal")
        builder.setItems(day) { dialog, which ->
            when (which) {
                0 -> {venuename.text="Kick Futsal Lalitpur" ; venuelocation.text="Bangalamukhi-Sankhamul Road"}
                1 -> {venuename.text="Shankhamul Futsal" ; venuelocation.text="Sankhamul Marga"}
                2 -> {venuename.text="Royal Futsal" ; venuelocation.text="Psuhpa Nagar Marg Marga"}
                3 -> {venuename.text="Shantinagar Futsal" ; venuelocation.text="Thulo Kharibot Marga"}
                4 -> {venuename.text="Prismatic Futsal and Recreation Center" ; venuelocation.text="Milap Road"}
                5 -> {venuename.text="Dhobighat Futsal" ; venuelocation.text="Jhamsikhel Marg"}
                6 -> {venuename.text="Maa Banglamukhi Futsal"; venuelocation.text="Chakupat" }
            }
        }
        selectvenuebutton.setOnClickListener {
            val dialog = builder.create()
            dialog.show()
        }

        datepick.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)) { v, i, i2, i3 ->
            var ii3=i3.toString()
            var ie2=i2+1
            var ii2=ie2.toString()
            if (i3<10) { ii3="0"+i3 }
            if (ie2<10) { ii2="0"+ie2}
            datepickval.text="$ii3/$ii2/$i"
        }

        timepicker.setOnTimeChangedListener { timePicker, i, i2 ->
            var ii2=i2.toString()
            if (i2<10) { ii2="0"+i2}
            timepickerval.text="$i:$ii2"
        }
        endtimepicker.setOnTimeChangedListener { timePicker, i, i2 ->
            var ii2=i2.toString()
            if (i2<10) { ii2="0"+i2}
            endtimepickerval.text="$i:$ii2"
        }

        fun createroom(venuname:String, venuloc:String, time1:String, time2:String, date:String, pricepp:String, phon:String, contact:String, tp:String, jp:String, uid:String, adinfo:String) {
            var totime="$time1-$time2"
            val room=LookingtoPlayRoom(venuname,venuloc,totime,date,pricepp,tp.toInt(),jp.toInt(),phon,contact,uid, adinfo)
            database.child("lookingtoplay").push().setValue(room)
                .addOnSuccessListener {
                    Navigation.findNavController(view).navigate(R.id.action_formFragment_LTP_to_tournamentSuccess)
                }
                .addOnFailureListener {
                    Toast.makeText(activity,"ERROR: $it", Toast.LENGTH_LONG).show()
                }
        }

        val submitalert = AlertDialog.Builder(context)



        submit.setOnClickListener {
         if (venuename.text.toString().trim().isBlank() || venuelocation.text.toString().trim().isBlank() || timepickerval.text.toString().trim().isBlank() || endtimepickerval.text.toString().trim().isBlank() ||
             datepickval.text.toString().trim().isBlank() || pricepp.text.toString().trim().isBlank() || phone.text.toString().trim().isBlank() || contacttext.text.toString().trim().isBlank() || totalplayers.text.toString().trim().isBlank()
             || joinedplayers.text.toString().trim().isBlank() || addinfo.text.toString().isBlank()) {
                Toast.makeText(activity, "Complete All Fields", Toast.LENGTH_LONG).show()
            } else {
             submitalert.setTitle("Is The Information Correct?")
             submitalert.setMessage("Venue Name : ${venuename.text.toString().trim()}"+ "\n" + "Start Time : " +
                     "${timepickerval.text.toString().trim()}" + "\n" +
                    "End Time : ${endtimepickerval.text.toString().trim()}" + "\n" +
                    "Date : ${datepickval.text.toString()}" + "\n" +
                    "Price Per Player : ${pricepp.text.toString().trim()}" + "\n" +
                    "Phone Number : ${phone.text.toString().trim()}" + "\n" +
                    "Other Contact : ${contacttext.text.toString().trim()}" + "\n" +
                    "Player Count : ${joinedplayers.text.toString().trim()}/${totalplayers.text.toString().trim()}"  + "\n" +
                    "Additional Info : ${addinfo.text.toString()}")
             submitalert.apply {
                 setPositiveButton("Submit",
                     DialogInterface.OnClickListener { dialog, id ->
                         createroom(
                             venuename.text.toString().trim(),
                             venuelocation.text.toString().trim(),
                             timepickerval.text.toString().trim(),
                             endtimepickerval.text.toString().trim(),
                             datepickval.text.toString(),
                             pricepp.text.toString().trim(),
                             phone.text.toString().trim(),
                             contacttext.text.toString().trim(),
                             totalplayers.text.toString().trim(),
                             joinedplayers.text.toString().trim(),
                             currentUser?.uid.toString(),
                             addinfo.text.toString()
                         )
                     })
                 setNegativeButton("Cancel",
                     DialogInterface.OnClickListener { dialog, id -> })
             }
             val alerto=submitalert.create()
             alerto.show()
            }
        }
    }
}