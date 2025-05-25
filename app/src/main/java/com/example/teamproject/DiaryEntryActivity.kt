package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.teamproject.Diary
import com.example.teamproject.DiaryDatabase
import com.example.teamproject.R
import com.example.teamproject.databinding.ActivityDiaryEntryBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DiaryEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emotionName = intent.getStringExtra("emotionName") ?: "감정 없음"
        val iconResId = intent.getIntExtra("iconResId", R.drawable.ic_happysticker)
        val selectedDate = intent.getStringExtra("selectedDate") ?: getTodayDate()

        val displayDate = formatDisplayDate(selectedDate)
        val dayOfWeek = getDayOfWeek(selectedDate)

        binding.tvDate.text = displayDate
        binding.tvEmotion.text = "$emotionName $dayOfWeek"
        binding.ivEmotion.setImageResource(iconResId)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnClose.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            val content = binding.etDiaryContent.text.toString()
            val diary = Diary(
                date = selectedDate,
                emotionName = emotionName,
                iconResId = iconResId,
                content = content
            )

            lifecycleScope.launch {
                DiaryDatabase.getDatabase(applicationContext).diaryDao().insert(diary)

                // 저장 후 HomeActivity로 이동
                val intent = Intent(this@DiaryEntryActivity, HomeActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date())
    }

    private fun formatDisplayDate(dateStr: String): String {
        val sdfInput = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val sdfOutput = SimpleDateFormat("M월 d일", Locale.KOREA)
        val date = sdfInput.parse(dateStr)
        return sdfOutput.format(date!!)
    }

    private fun getDayOfWeek(dateStr: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = sdf.parse(dateStr)
        val cal = Calendar.getInstance()
        cal.time = date!!
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "일요일"
            Calendar.MONDAY -> "월요일"
            Calendar.TUESDAY -> "화요일"
            Calendar.WEDNESDAY -> "수요일"
            Calendar.THURSDAY -> "목요일"
            Calendar.FRIDAY -> "금요일"
            Calendar.SATURDAY -> "토요일"
            else -> ""
        }
    }
}
