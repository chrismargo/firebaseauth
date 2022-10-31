package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaseauth.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.button.setOnClickListener{

            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passET.text.toString().trim()
            val confirmPass = binding.confirmPassEt.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    try{
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                            it.exception?.printStackTrace()
                            if(it.isSuccessful){
                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this,"Account registration failed. Please try again",Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch(e: Exception){
                        Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT)
                    }

                }
                else{
                    Toast.makeText(this,"Password does not match. Please try again",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Empty fields are not allowed. Please make sure all fields are filled",Toast.LENGTH_SHORT).show()
            }
        }
    }
}