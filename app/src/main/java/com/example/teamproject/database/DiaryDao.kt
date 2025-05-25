package com.example.teamproject

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiaryDao {
    @Insert
    suspend fun insert(diary: Diary)

    @Query("SELECT * FROM diary ORDER BY id DESC")
    fun getAll(): LiveData<List<Diary>>

    @Query("SELECT * FROM diary WHERE date = :date LIMIT 1")
    suspend fun getDiaryByDate(date: String): Diary?

}
