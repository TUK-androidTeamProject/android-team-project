package com.example.teamproject

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.databinding.ActivityAgreeBinding

class AgreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityAgreeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewText.paintFlags = binding.viewText.paintFlags

        binding.apply {

            val requiredCheckboxes = listOf(checkPrivacy)
            val allCheckboxes = listOf(checkPrivacy, checkAd)

            fun updateAgreeBtn() {
                val allRequiredChecked = requiredCheckboxes.all { it.isChecked }
                agreeButton.isEnabled = allRequiredChecked
                agreeButton.backgroundTintList= ColorStateList.valueOf(
                    if (allRequiredChecked) {
                        Color.parseColor("#FEE500")
                    } else {
                        Color.parseColor("#CCCCCC")
                    }
                )
            }

            var isUpdating = false  // 체크 상태 업데이트 중인지 표시

            checkAll.setOnCheckedChangeListener { _, isChecked ->
                if (!isUpdating) {
                    isUpdating = true
                    allCheckboxes.forEach { it.isChecked = isChecked } // 전체 체크 or 해제
                    isUpdating = false
                    updateAgreeBtn()
                }
            }

            allCheckboxes.forEach { checkbox ->
                checkbox.setOnCheckedChangeListener { _, _ ->
                    if (!isUpdating) {
                        isUpdating = true
                        checkAll.isChecked = allCheckboxes.all { it.isChecked }
                        isUpdating = false
                        updateAgreeBtn()
                    }
                }
            }

            updateAgreeBtn()

        }
        binding.agreeButton.setOnClickListener {
            val intent= Intent(this, SetNameActivity::class.java)
            startActivity(intent)
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }


    }
}