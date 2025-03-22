package com.example.registeration

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class register : AppCompatActivity() {

    var registerButton: Button? = null
    var name: EditText? = null
    var contact: EditText? = null
    var address: EditText? = null
    var age: EditText? = null
    private lateinit var bloodGroupSpin: Spinner
    private lateinit var dbHelper: donord
    private lateinit var db: SQLiteDatabase
    var FieldsChecked = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = donord(this)
        db = dbHelper.writableDatabase


        registerButton = findViewById(R.id.button3)
        name = findViewById(R.id.editTextText2)

        val bloodGroupOptio = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        bloodGroupSpin = findViewById(R.id.spinner2)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroupOptio)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpin.adapter = adapter2

        contact = findViewById(R.id.editTextPhone2)
        age = findViewById(R.id.editTextNumberSigned)
        address = findViewById(R.id.editTextTextPostalAddress2)

        val reb=registerButton as Button
        reb.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                FieldsChecked=checkAllFields()
                if (FieldsChecked) {
                        insertDonor()
                    }

                }

        })
    }

    fun checkAllFields(): Boolean {
        if (name!!.text.isBlank()) {
            name!!.error = "This field is required"
            return false
        }

        if (address!!.text.isBlank()) {
            address!!.error = "Address is required"
            return false
        }

        if (contact!!.text.isBlank()) {
            contact!!.error = "Contact number is required"
            return false
        } else if (contact!!.text.length != 10) {
            contact!!.error = "Contact number is invalid"
            return false
        }

        val ageText = age!!.text.toString()
        if (ageText.isBlank()) {
            age!!.error = "Age is required"
            return false
        }

        val ageInt = ageText.toInt()
        if (ageInt !in 18..60) {
            age!!.error = "Not eligible for donating blood"
            return false
        }
        return true
    }


    @SuppressLint("SuspiciousIndentation")
    fun insertDonor() {
        val bloodGroup = bloodGroupSpin.selectedItem.toString()

        val data = ContentValues().apply {
            put(donord.COLUMN_NAME, name?.text.toString())
            put(donord.COLUMN_LOCATION, address?.text.toString())
            put(donord.COLUMN_CONTACT, contact?.text.toString())
            put(donord.COLUMN_BLOOD_TYPE, bloodGroup)
        }

        val newRow = db.insert(donord.TABLE_DONOR2, null, data)

        if (newRow == -1L) {
            Toast.makeText(this, "Error inserting donor", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, name?.text.toString()+" registered successfully", Toast.LENGTH_SHORT).show()
            val intt=Intent(this@register,home::class.java)
            startActivity(intt)
        }
    }
}