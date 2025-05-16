package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var textDayInfo: TextView
    private lateinit var btnWriteDiary: Button
    private lateinit var binding: ActivityHomeBinding

    private var selectedDate: String = ""
    private val diaryMap = mutableMapOf<String, String>() // 날짜별 일기 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calendarView = binding.calendarView
        textDayInfo = binding.textDayInfo
        btnWriteDiary = binding.btnWriteDiary

        selectedDate = getDateFromMillis(calendarView.date)
        updateDayInfo(selectedDate)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
            updateDayInfo(selectedDate)
        }

        btnWriteDiary.setOnClickListener {
            if (diaryMap.containsKey(selectedDate)) {
                // ▶ 일기 보기
                val intent = Intent(this, DiaryViewActivity::class.java)
                intent.putExtra("selectedDate", selectedDate)
                intent.putExtra("diaryText", diaryMap[selectedDate])
                Log.d("test123","${diaryMap[selectedDate]}")
                startActivity(intent)
            } else {
                // ▶ 일기 쓰기
                val intent = Intent(this, DiaryActivity::class.java)
                intent.putExtra("selectedDate", selectedDate)
                startActivityForResult(intent, 100)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val date = data.getStringExtra("selectedDate") ?: return
            val diary = data.getStringExtra("diaryText") ?: return

            diaryMap[date] = diary
            Toast.makeText(this, "$date 일기 저장됨", Toast.LENGTH_SHORT).show()
            updateDayInfo(date)
        }
    }

    private fun updateDayInfo(date: String) {
        val dayOfWeek = getDayOfWeek(date)
        val info = if (diaryMap.containsKey(date)) {
            "$date $dayOfWeek\n작성된 일기가 있어요!"
        } else {
            "$date $dayOfWeek\n작성된 일기가 없어요"
        }
        textDayInfo.text = info
        btnWriteDiary.text = if (diaryMap.containsKey(date)) "일기 보기" else "일기 쓰기"
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