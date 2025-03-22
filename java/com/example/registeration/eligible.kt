package com.example.registeration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.registeration.R.id.checkBox2
import com.example.registeration.R.id.checkBox3

class eligible : AppCompatActivity() {

    private lateinit var ageEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var lastDonationEditText: EditText
    private lateinit var chronicDiseasesCheckBox: CheckBox
    private lateinit var smokeCheckBox: CheckBox
    private lateinit var submitButton: Button
    private lateinit var heocheck: CheckBox
    private lateinit var tato: CheckBox

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eligible)

        ageEditText = findViewById(R.id.editTextNumberSigned)
        weightEditText = findViewById(R.id.etWeight)
        lastDonationEditText = findViewById(R.id.etLastDonation)
        chronicDiseasesCheckBox = findViewById(R.id.cbChronicDiseases)
        smokeCheckBox = findViewById(R.id.checkBox)
        heocheck = findViewById(checkBox2)
        tato = findViewById(checkBox3)
        submitButton = findViewById(R.id.submitButton)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submitButton.setOnClickListener {
            if (validateInputs()) {
                Toast.makeText(this, "Validation passed!", Toast.LENGTH_SHORT).show()


                val regis= Intent(this, register::class.java);
                startActivity(regis)

            }
            else{
                Toast.makeText(this, "You are not eligible!", Toast.LENGTH_SHORT).show()

                val regis= Intent(this, home::class.java);
                startActivity(regis)

            }
        }
    }

    private fun validateInputs(): Boolean {
        val ageText = ageEditText.text.toString()
        val weightText = weightEditText.text.toString()
        val isSmoker = smokeCheckBox.isChecked
        val isdis = chronicDiseasesCheckBox .isChecked
        val h = heocheck .isChecked
        val t = tato .isChecked

        if (ageText.isEmpty()) {
            ageEditText.error = "Age is required"
            return false
        }

        val age = ageText.toIntOrNull()
        if (age == null || (age < 18&& age>65)) {
            ageEditText.error = "Valid age (18 or older) is required"
            return false
        }

        if (weightText.isEmpty()) {
            weightEditText.error = "Weight is required"
            return false
        }

        val weight = weightText.toFloatOrNull()
        if (weight == null || weight <= 0) {
            weightEditText.error = "Valid weight is required"
            return false
        }

        if (isSmoker) {
            return false
        }
        if (isdis) {

            return false
        }
        if (!h) {
            return false
        }
        if (t) {
            return false
        }

        return true
    }
}