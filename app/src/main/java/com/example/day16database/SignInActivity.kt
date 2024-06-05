package com.example.day16database

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    companion object{
        const val  KEY1 = "com.example.day16database.SignInActivity.mail"
        const val  KEY2 = "com.example.day16database.SignInActivity.name"
        const val  KEY3 = "com.example.day16database.SignInActivity.id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signInButton = findViewById<Button>(R.id.btnSignin)
        val userName  = findViewById<TextInputEditText>(R.id.userNameEditText)
        signInButton.setOnClickListener {
            val userNameString = userName.text.toString()
            if(userNameString.isNotEmpty()){
                readData(userNameString)

            }else{
                Toast.makeText(this,"Please enter user name",Toast.LENGTH_SHORT).show()

            }
        }
    }// On create method over

    private fun readData(userNameString: String) {
        // this will give me the details for the users
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        // Abhi hume users tak ka address pta chala gya hai toh hum yahan se uske child ka date get karenge
        databaseReference.child(userNameString).get().addOnSuccessListener {
            // Now we have to the check the given user is exist in our database or not?
            if(it.exists()){
                // Then we will welcome our user
                val email = it.child("email").value
                val name = it.child("name").value
                val userId = it.child("uniqueId").value


                val intentWelcome = Intent(this,WelcomeActivity::class.java)
                intentWelcome.putExtra(KEY1,email.toString())
                intentWelcome.putExtra(KEY2,name.toString())
                intentWelcome.putExtra(KEY3,userId.toString())
                startActivity(intentWelcome)
            }else{
                Toast.makeText(this,"User does not exist",Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener {
            Toast.makeText(this,"Failed, Error in DB",Toast.LENGTH_SHORT).show()
        }

    }
}