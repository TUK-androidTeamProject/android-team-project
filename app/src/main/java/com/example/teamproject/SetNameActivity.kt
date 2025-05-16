package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teamproject.databinding.ActivitySetNameBinding

class SetNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var currNum=0
        val binding= ActivitySetNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 사용 안 해도 됨
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 글자가 바뀔 때마다 호출
                binding.btnDone.isEnabled = !s.isNullOrEmpty()
                currNum=(binding.editText.text.toString()).length
                binding.numMax.text="$currNum/10"
            }

            override fun afterTextChanged(s: Editable?) {
                // 사용 안 해도 됨
            }
        })
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDone.setOnClickListener {
            val intent= Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}