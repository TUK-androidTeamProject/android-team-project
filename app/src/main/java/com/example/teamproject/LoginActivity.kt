package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.loginBtn.setOnClickListener {
            val intent= Intent(this,AgreeActivity::class.java)
            startActivity(intent)
        }
        binding.editTextPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.editTextPw.text.toString().length>8)
                    binding.login.isEnabled=true
                else
                    binding.login.isEnabled=false
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
