package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teamproject.databinding.ActivityDiaryViewBinding

class DiaryViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiaryViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val date = intent.getStringExtra("selectedDate")
        val text = intent.getStringExtra("diaryText")
        val emotionName = intent.getStringExtra("emotionName") ?: "감정 없음"
        val iconResId = intent.getIntExtra("iconResId", R.drawable.ic_happysticker)

        // 본문 출력
        binding.textDiaryView.text = "$date 의 일기\n\n$text"

        // 감정 정보 출력
        binding.tvEmotionText.text = emotionName
        binding.ivEmotionIcon.setImageResource(iconResId)

        // 뒤로가기 & 닫기 -> HomeActivity로 이동
        binding.btnClose.setOnClickListener {
            goToMain()
        }

        binding.btnBack.setOnClickListener {
            goToMain()
        }
    }
    private fun goToMain() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
