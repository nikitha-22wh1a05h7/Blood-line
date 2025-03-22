package com.example.registeration

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.telephony.SmsManager
import android.widget.Toast
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity2 : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    private lateinit var bloodGroupSpinner: Spinner

    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    SEND_SMS_PERMISSION_REQUEST_CODE
            )
        }
        val bloodGroupOptions = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        bloodGroupSpinner = findViewById(R.id.spinner2)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroupOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpinner.adapter = adapter

        linearLayout = findViewById(R.id.linear)

        val buttonSearch = findViewById<Button>(R.id.button)
        buttonSearch.setOnClickListener {
            searchDonor()
        }
    }

    @SuppressLint("Range", "SetTextI18n")
    private fun searchDonor() {
        val locationEditText = findViewById<EditText>(R.id.editTextText)
        val collectloc = findViewById<EditText>(R.id.editTextText3)
        val bloodGroup = bloodGroupSpinner.selectedItem.toString()
        val location = locationEditText.text.toString()
        val loc=collectloc.text.toString()

        val dbHelper = donord(this)
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${donord.TABLE_DONOR2} WHERE ${donord.COLUMN_BLOOD_TYPE} = ? AND ${donord.COLUMN_LOCATION} = ?",
            arrayOf(bloodGroup, location)
        )

        linearLayout.removeAllViews()
        val textSize = resources.getDimension(R.dimen.text_size_large)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(donord.COLUMN_NAME))
                val contact = cursor.getString(cursor.getColumnIndex(donord.COLUMN_CONTACT))
                
                val textView = TextView(this)
                textView.text = "\nName: $name\nContact: $contact\n\n"
                textView.textSize = textSize
                linearLayout.addView(textView)
                val smsButton = Button(this)
                smsButton.text = "Send SMS"
                smsButton.setOnClickListener {
                    sendSMS(contact, "Hello $name, we need your help!.We require blood at $loc")
                }
                linearLayout.addView(smsButton)
            } while (cursor.moveToNext())
        } else {
            val textView = TextView(this)
            textView.text = "No donors found for the specified criteria."
            textView.textSize = textSize
            linearLayout.addView(textView)
        }

        cursor.close()
        db.close()
    }
    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent successfully.", Toast.LENGTH_SHORT).show()
        }  catch (ex: SecurityException) {
            Toast.makeText(this, "Permission denied to send SMS.", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        } catch (ex: Exception) {
            Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }
}