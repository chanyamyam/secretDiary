package com.app.part2.chapter3.secretdiary

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.app.part2.chapter3.secretdiary.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val numberPicker1: NumberPicker by lazy {
        binding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker2: NumberPicker by lazy {
        binding.numberPicker2.apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker3: NumberPicker by lazy {
        binding.numberPicker3.apply {
            minValue = 0
            maxValue = 9
        }
    }
    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        numberPicker1
        numberPicker2
        numberPicker3

        binding.openButton.setOnClickListener {
            if(changePasswordMode) {
                Toast.makeText(this,"비밀번호 변경중",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(passwordPreferences.getString("password","000").equals(passwordFromUser)) {
                // 로그인 성공

                //TODO 다이어리 페이지 작성 후에 넘겨주어야함
                //startActivity()
            } else {
                // 실패
                showErrorAlert()
            }
        }
        binding.changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(changePasswordMode) {
                // 설정한 번호 저장
                passwordPreferences.edit(true) {
                    putString("password" , passwordFromUser)
                }
                changePasswordMode=false
                binding.changePasswordButton.setBackgroundColor(Color.BLACK)
            } else {
                // Mode 활성화&&비밀번호가 맞는지 확인

                if(passwordPreferences.getString("password","000").equals(passwordFromUser)) {
                    // 로그인 성공
                    changePasswordMode = true
                    Toast.makeText(this,"변경할 패스워드를 입력해주세요!", Toast.LENGTH_SHORT).show()
                    Log.d("ActivityMain", changePasswordMode.toString())
                    binding.changePasswordButton.setBackgroundColor(Color.RED)
                    //TODO 다이어리 페이지 작성 후에 넘겨주어야함
                    //startActivity()
                } else {
                    // 실패
                    showErrorAlert()
                }
            }
        }
        val view = binding.root
        setContentView(view)
    }

    private fun showErrorAlert() {
        AlertDialog.Builder(this).setTitle("실패!")
            .setMessage("비밀번호가 잘못되었습니다")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}