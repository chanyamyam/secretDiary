package com.app.part2.chapter3.secretdiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.app.part2.chapter3.secretdiary.databinding.ActivityDiaryBinding

class DiaryActivity: AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivityDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        val view = binding.root
        val diaryEditText = binding.diaryEditText
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail",""))


        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail",diaryEditText.text.toString())
            }
        }

        diaryEditText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
        setContentView(view)

    }
}