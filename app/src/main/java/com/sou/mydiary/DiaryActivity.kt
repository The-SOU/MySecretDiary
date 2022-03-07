package com.sou.mydiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.sou.mydiary.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryBinding

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        binding.diaryEditText.setText(detailPreferences.getString("diary", ""))


        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", binding.diaryEditText.text.toString())
            }

            Log.d("DiaryActivity", "SAVE ${binding.diaryEditText.text.toString()}")
        }

        binding.diaryEditText.addTextChangedListener {

            Log.d("DiaryActivity", "TextChanged :: $it")
            //애드 택스트 체인지 리스너에 들어오면 이전에 있던 러너블 지우고(없으면 동작안하고), 0.5초 뒤에 이 러너블을 실행시키는 메시지를 핸들러에 전달
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)

            }
        }


    }