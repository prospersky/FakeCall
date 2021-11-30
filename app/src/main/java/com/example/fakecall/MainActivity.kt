package com.example.fakecall

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var count1 = 0
    private var setHour = 0
    private var setMinute = 0
    private var resHour = 0
    private var resMinute = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateAndtime: LocalDateTime = LocalDateTime.now()
        val onlyDate: LocalDate = LocalDate.now()
        val setTime: TimePicker = findViewById(R.id.timepicker)

        println("Current date and time: $dateAndtime")
        println("Current date: $onlyDate")

        val startButton: Button = findViewById(R.id.button1)
        startButton.setOnClickListener {
            startTimer()
        }

        val stopButton: Button = findViewById(R.id.button2)
        stopButton.setOnClickListener {

            setHour = setTime.hour;
            setMinute = setTime.minute;

            println("Setting Hour : $setHour")
            println("Setting Minute : $setMinute")

  //          addAlarm();
        }
    }

    private fun startTimer() {
        val timer = timer(period = 1000) {
            count1++
            runOnUiThread {
                val timerTextView : TextView = findViewById(R.id.textView)
                timerTextView.text = "START"
            }

            if (count1 == 3) {
                count1 = 0
                cancel()
                println("타이머 종료")
            }
        }
    }

    private fun setAlarm(){
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var now = Calendar.getInstance()
        var calendarList = ArrayList<Calendar>()

        for (i in 1..number)
        cal.set(Calendar.YEAR, 2021)
        cal.set(Calendar.MONTH, 11)
        cal.set(Calendar.DAY_OF_MONTH, 25)
        cal.set(Calendar.HOUR_OF_DAY, 1)
        cal.set(Calendar.MINUTE, 56)
        cal.set(Calendar.SECOND, 0)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pIntent)

        println("알람11111111111111111111111111")
    }
}

class Alarm: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null){
            Log.e("알람", System.currentTimeMillis().toString())
            println("알람2222222222222")
        }
    }
}

