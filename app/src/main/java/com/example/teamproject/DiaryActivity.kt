package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teamproject.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryBinding

    private lateinit var editDiary: EditText
    private lateinit var btnSave: Button
    private lateinit var selectedDate: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editDiary = binding.editDiary
        btnSave = binding.btnSave

        selectedDate = intent.getStringExtra("selectedDate") ?: ""

        btnSave.setOnClickListener {
            val diaryText = editDiary.text.toString()

            val resultIntent = Intent().apply {
                putExtra("selectedDate", selectedDate)
                putExtra("diaryText", diaryText)
            }
            setResult(RESULT_OK, resultIntent)
            finish() // MainActivity로 돌아감
        }
    }
}