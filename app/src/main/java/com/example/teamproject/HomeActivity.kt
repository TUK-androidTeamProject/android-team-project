package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.teamproject.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDate = getDateFromMillis(binding.calendarView.date)
        updateDayInfo(selectedDate)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
            updateDayInfo(selectedDate)
        }

        binding.btnWriteDiary.setOnClickListener {
            lifecycleScope.launch {
                val diaryDao = DiaryDatabase.getDatabase(applicationContext).diaryDao()
                val diary = diaryDao.getDiaryByDate(selectedDate)

                Log.d("DiaryCheck", "버튼 클릭 시 조회 날짜: $selectedDate, diary: $diary")

                if (diary != null) {
                    val intent = Intent(this@HomeActivity, DiaryViewActivity::class.java)
                    intent.putExtra("selectedDate", selectedDate)
                    intent.putExtra("diaryText", diary.content)
                    intent.putExtra("emotionName", diary.emotionName)       // ✅ 감정 이름 추가
                    intent.putExtra("iconResId", diary.iconResId)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@HomeActivity, EmotionSelectionActivity::class.java)
                    intent.putExtra("selectedDate", selectedDate)
                    startActivity(intent)
                }
            }
        }
    }

    private fun updateDayInfo(date: String) {
        val dayOfWeek = getDayOfWeek(date)

        lifecycleScope.launch {
            val diaryDao = DiaryDatabase.getDatabase(applicationContext).diaryDao()
            val diary = diaryDao.getDiaryByDate(date)

            Log.d("DiaryCheck", "updateDayInfo 조회 날짜: $date, diary: $diary")

            val info = if (diary != null) {
                "$date $dayOfWeek\n작성된 일기가 있어요!"
            } else {
                "$date $dayOfWeek\n작성된 일기가 없어요"
            }

            binding.textDayInfo.text = info
            binding.btnWriteDiary.text = if (diary != null) "일기 보기" else "일기 쓰기"
        }
    }

    private fun getDateFromMillis(millis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        return sdf.format(Date(millis))
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
