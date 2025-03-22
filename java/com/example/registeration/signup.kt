package com.example.registeration

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class signup : AppCompatActivity() {
    var sign: Button? = null
    var username: EditText? = null
    var password: TextInputLayout? = null
    var confirmpassword: TextInputLayout? = null
    lateinit var dbHelper2: userdata
    lateinit var db: SQLiteDatabase

    var isAllFieldsChecked = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        dbHelper2 = userdata(this)
        db = dbHelper2.writableDatabase

        sign = findViewById(R.id.sign)
        val bu = sign as Button

        username = findViewById(R.id.txt2)
        password = findViewById(R.id.editTextTextPassword3)
        confirmpassword = findViewById(R.id.editTextTextPassword2)

        bu.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                isAllFieldsChecked = CheckAllFields()


                if (isAllFieldsChecked) {
                    insertUser()
                    val i = Intent(this@signup, home::class.java)
                    startActivity(i)
                }
            }
        })

    }


    fun CheckAllFields(): Boolean {
        if (username!!.length() == 0) {
            username!!.error = "This field is required"
            return false
        }

        if (password!!.editText?.text?.isEmpty() == true) {
            Toast.makeText(this, "Password is required",Toast.LENGTH_SHORT).show()

            return false
        } else if (password!!.editText?.text?.length!! < 6) {
            Toast.makeText(this, "Password must be minimum 6 characters",Toast.LENGTH_SHORT).show()
            return false
        }

        if (confirmpassword!!.editText?.text?.isEmpty() == true) {
            Toast.makeText(this, "Confirm Password is required",Toast.LENGTH_SHORT).show()
            return false
        }else if (!password!!.editText?.text.toString().equals(confirmpassword!!.editText?.text?.toString())){
            Toast.makeText(this, "Password must be same as confirm password",Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    private fun insertUser() {
        val values = ContentValues().apply {
            put(userdata.COLUMN_USERNAME, username?.text.toString())
            put(userdata.COLUMN_PASSWORD, password?.editText?.text?.toString())
        }

        val newRowId = db.insert(userdata.TABLE_USERS, null, values)

        if (newRowId == -1L) {
            Toast.makeText(this, "Error inserting user", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "User signed in successfully", Toast.LENGTH_SHORT).show()
        }
    }


}
