package com.example.registeration

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout


class login : AppCompatActivity() {
    var login: Button? = null
    lateinit var dbHelper1: userdata
    lateinit var db: SQLiteDatabase

    var username: EditText? = null
    var password: TextInputLayout? = null


    var isAllFieldsChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login = findViewById(R.id.sign)
        val button = login as Button
        dbHelper1 = userdata(this)
        db = dbHelper1.readableDatabase


        username = findViewById(R.id.txt2)
        password = findViewById(R.id.editTextTextPassword3)

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                isAllFieldsChecked = CheckAllFields()


                if (isAllFieldsChecked) {
                    val enteredUsername = username?.text.toString()
                    val enteredPassword = password?.editText?.text.toString()
                    if (enteredUsername.isNotEmpty() && enteredPassword.isNotEmpty()) {
                        if (validateCredentials(enteredUsername, enteredPassword)) {
                            Toast.makeText(this@login, enteredUsername+" is logged in successfully!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@login, home::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@login, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        })

    }


    fun CheckAllFields(): Boolean {
        if (username!!.length() == 0) {
            username!!.error = "This field is required"
            return false
        }

        if (password!!.editText?.text?.isBlank() == true) {
            Toast.makeText( this,"Password is required",Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    private fun validateCredentials(username: String, password: String): Boolean {
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${userdata.TABLE_USERS} WHERE ${userdata.COLUMN_USERNAME} = ? AND ${userdata.COLUMN_PASSWORD} = ?",
            arrayOf(username, password)
        )

        val result = cursor.count > 0
        cursor.close()
        return result
    }

}
