package com.example.fakecall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

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