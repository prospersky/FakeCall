package com.example.fakecall

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import kotlin.system.exitProcess
import java.util.*


class AnswerActivity : AppCompatActivity() {

    private var timerTask: Timer? = null
    private var time = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val secondEditText: TextView = findViewById(R.id.textView_second)

        //calltimeEditText.text = ((System.currentTimeMillis() - startTime)/1000).toString()
        val callerImage: ImageView = findViewById(R.id.imageView_face2)
        val uri = intent.getParcelableExtra<Uri>("uri")
        callerImage.setImageURI(uri)

        timerTask = kotlin.concurrent.timer(period = 1000) {	// timer() 호출
            time++	// period=10, 0.01초마다 time를 1씩 증가
            var second = time % 60 // 나눗셈의 몫 (초 부분)
            var minute = time / 60 // 나눗셈의 나머지 (밀리초 부분)

            // UI조작을 위한 메서드
            runOnUiThread {
                secondEditText.text = "$minute : $second"    // TextView 세팅
            }
        }

        val finishButton: ImageButton = findViewById(R.id.button_finish)
        finishButton.setOnClickListener {
//            val incomingIntent = Intent(this, MainActivity::class.java)
//            incomingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(incomingIntent)

            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }
    }
}