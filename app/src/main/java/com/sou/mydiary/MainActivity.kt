package com.sou.mydiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.sou.mydiary.databinding.ActivityDiaryBinding
import com.sou.mydiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initNumberPicker()

        binding.openButton.setOnClickListener {
            setPasswordButton()
        }

        binding.changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"

            if (changePasswordMode) {
                passwordPreferences.edit(commit = true) {
                    putString("password", passwordFromUser)
                }
                changePasswordMode = false
                binding.changePasswordButton.setBackgroundColor(Color.BLACK)
            } else {
                //changePasswordMode 가 활성화 - 비밀번호가 맞는지 체크

                //패스워드 초기값 설정
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {

                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    binding.changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    //실패
                    showErrorAlertDialog()
                }

            }
        }

    }

    private fun initNumberPicker() {
        binding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }

        binding.numberPicker2.apply {
            minValue = 0
            maxValue = 9
        }

        binding.numberPicker3.apply {
            minValue = 0
            maxValue = 9
        }
    }

    private fun setPasswordButton() {
        if (changePasswordMode) {
            Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
        }

        val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

        val passwordFromUser =
            "${binding.numberPicker1.value}${binding.numberPicker2.value}${binding.numberPicker3.value}"

        //패스워드 초기값 설정
        if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
            //패스워드 성공
            startActivity(Intent(this, DiaryActivity::class.java))
        } else {
            //실패
            showErrorAlertDialog()
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}