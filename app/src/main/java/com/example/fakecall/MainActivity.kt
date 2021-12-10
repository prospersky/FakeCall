package com.example.fakecall

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import java.lang.System.currentTimeMillis
import java.util.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timePicker: TimePicker = findViewById(R.id.timepicker)
        lateinit var toast: Toast

        val nameEditText: EditText = findViewById(R.id.editText_name)

        // Alarm manager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val bundle = Bundle()
        var pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        var pendingIntent: PendingIntent
/*
          // Broadcast Receiver
//        val alarmReceiver : BroadcastReceiver = AlarmReceiver()
//        val filter = IntentFilter().apply{
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        registerReceiver(alarmReceiver, filter)
*/
        toast = Toast.makeText(this, "Please setting calling time", Toast.LENGTH_LONG)
        toast.show()

        // Alarm set up Button Click event
        val startButton: ImageButton = findViewById(R.id.button_save)
        startButton.setOnClickListener {
            // Except set current time
            if (timePicker.minute == Calendar.getInstance().get(Calendar.MINUTE)) {
                toast.cancel()
                toast = Toast.makeText(this, "Cannot set current minute", Toast.LENGTH_LONG)
                toast.show()
            }
            else {
                val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, timePicker.hour)
                    set(Calendar.MINUTE, timePicker.minute)
                    set(Calendar.SECOND, 0) // to operate on time
                }

                bundle.putString("Caller Name",nameEditText.text.toString())
                intent.putExtra("bundle",bundle)

                println("선택 시간 : " + timePicker.hour + ", 선택 분 : " + timePicker.minute)

//                intent.putExtra("Caller Name",nameEditText.text.toString())

                println("nameEditText : " + nameEditText.text.toString())
                //println("getStringExtra : " + intent.getStringExtra("Caller Name"))
                println("getStringExtra : " + intent.getBundleExtra("bundle")!!.getString("Caller Name"))

                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

               //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                this.finish()
            }
        }

        // Alarm cancel button Click event
        val stopButton: ImageButton = findViewById(R.id.button_cancel)
        stopButton.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "Cancel calling", Toast.LENGTH_LONG)
            toast.show()
            alarmManager.cancel(pendingIntent)
        }

        val testButton: Button = findViewById(R.id.button_10min)
        testButton.setOnClickListener {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis(), pendingIntent)
            this.finish()
        }
    }
}

