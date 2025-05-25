package com.example.teamproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class Diary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val emotionName: String,
    val iconResId: Int,
    val content: String
)
