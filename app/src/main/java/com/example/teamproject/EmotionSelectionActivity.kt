package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EmotionSelectionActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_selection)

        val selectedDate = intent.getStringExtra("selectedDate") ?: getTodayDate()

        recyclerView = findViewById(R.id.rvEmotions)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = EmotionAdapter(EmotionData.emotions) { selectedEmotion ->
            // 감정 선택 시
            val intent = Intent(this, DiaryEntryActivity::class.java).apply {
                putExtra("emotionName", selectedEmotion.name)
                putExtra("iconResId", selectedEmotion.iconResId)
                putExtra("selectedDate", selectedDate)
            }
            startActivity(intent)
        }

        // ✅ 건너뛰기 클릭 시
        val skipTextView = findViewById<TextView>(R.id.tvSkip)
        skipTextView.setOnClickListener {
            val intent = Intent(this, DiaryEntryActivity::class.java).apply {
                putExtra("emotionName", "감정을 모르겠는")
                putExtra("iconResId", R.drawable.ic_question) // 기본 아이콘으로 대체하세요
                putExtra("selectedDate", selectedDate)
            }
            startActivity(intent)
        }
        // 뒤로 가기 버튼 클릭
        findViewById<ImageView>(R.id.ivClose).setOnClickListener {
            goToMain()
        }
    }


    private fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date())
    }

    private fun goToMain() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
