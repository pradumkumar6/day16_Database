package com.example.day16database

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var  database : DatabaseReference  //(initialize the database) here we make variable database with type DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signButton = findViewById<Button>(R.id.btnSignup)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etMail = findViewById<TextInputEditText>(R.id.etMail)
        val userId = findViewById<TextInputEditText>(R.id.etUserId)
        val userPassword = findViewById<TextInputEditText>(R.id.etPassword)
        signButton.setOnClickListener {
            // stores the users data
            val name = etName.text.toString()
            val mail = etMail.text.toString()
            val uniqueId = userId.text.toString()
            val password = userPassword.text.toString()

            // create an object of User class
            val users = Users(name,mail,password,uniqueId)
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(uniqueId).setValue(users).addOnSuccessListener {
                etName.text?.clear()
                etMail.text?.clear()
                userId.text?.clear()
                userPassword.text?.clear()
                Toast.makeText(this,"User Registered Successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
}