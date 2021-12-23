package com.example.fakecall


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import java.lang.System.currentTimeMillis
import java.util.*
import android.graphics.Bitmap
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.fakecall.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding?= null
    private val binding get() = mBinding!!

    private val bundle = Bundle()

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        binding.imageViewPreview.setImageURI(result.data?.data)
        println("result : " + result.data?.data)
        bundle.putParcelable("uri", result.data?.data)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var toast: Toast
        val timePicker: TimePicker = findViewById(R.id.timepicker)
        val nameEditText: EditText = findViewById(R.id.editText_name)

        // Alarm manager
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
//        val bundle = Bundle()
        var pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
/*
          // Broadcast Receiver
//        val alarmReceiver : BroadcastReceiver = AlarmReceiver()
//        val filter = IntentFilter().apply{
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        registerReceiver(alarmReceiver, filter)
*/
        if( (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA),0)
        }

        toast = Toast.makeText(this, "예약 시간을 설정바람", Toast.LENGTH_LONG)
        toast.show()

        // Alarm set up Button Click event
        binding.buttonSave.setOnClickListener {
            // Except set current time
            if ( (timePicker.minute <= Calendar.getInstance().get(Calendar.MINUTE)) && (timePicker.hour <= Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) ) {
                toast.cancel()
                toast = Toast.makeText(this, "현재 및 이전 시간 설정 불가", Toast.LENGTH_LONG)
                toast.show()
            }
            else {
                val calendar: Calendar = Calendar.getInstance().apply {
                    timeInMillis = currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, timePicker.hour)
                    set(Calendar.MINUTE, timePicker.minute)
                    set(Calendar.SECOND, 0) // to operate on time
                }

                var defaultName = "엄마"
                if(nameEditText.text.toString() == "") {
                    nameEditText.setText(defaultName)
                    println("null : " + nameEditText.text.toString())
                }
                else {
                    defaultName = nameEditText.text.toString()
                    println("else : " + nameEditText.text.toString())
                }

//                val bitmap = binding.imageViewPreview.drawable.toBitmap()
//
//                val stream =  ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//
//                val bytes = stream.toByteArray();
//                bundle.putByteArray("image", bytes) )
                bundle.putString("Caller Name",defaultName)
                intent.putExtra("bundle",bundle)

//                println("선택 시간 : " + timePicker.hour + ", 선택 분 : " + timePicker.minute)
//                println("nameEditText : " + nameEditText.text.toString())
//                println("getStringExtra : " + intent.getBundleExtra("bundle")!!.getString("Caller Name"))

                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                this.finish()

                toast.cancel()
                toast = Toast.makeText(this, "설정 완료", Toast.LENGTH_LONG)
                toast.show()
            }
        }
        // Alarm cancel button Click event
        binding.buttonCancel.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "예약 전화 취소됨", Toast.LENGTH_LONG)
            toast.show()
            alarmManager.cancel(pendingIntent)
        }
        binding.button1min.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "예약 설정 - 1분뒤", Toast.LENGTH_LONG)
            toast.show()
            
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis()+60000, pendingIntent)
            this.finish()
        }
        binding.button5min.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "예약 설정 - 5분뒤", Toast.LENGTH_LONG)
            toast.show()

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis()+300000, pendingIntent)
            this.finish()
        }
        binding.button10min.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "예약 설정 - 10분뒤", Toast.LENGTH_LONG)
            toast.show()

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis()+600000, pendingIntent)
            this.finish()
        }
        binding.button30min.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "예약 설정 - 30분뒤", Toast.LENGTH_LONG)
            toast.show()

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis()+1800000, pendingIntent)
            this.finish()
        }
        binding.button1hour.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(this, "예약 설정 - 1시간뒤", Toast.LENGTH_LONG)
            toast.show()

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis()+3600000, pendingIntent)
            this.finish()
        }
        binding.buttonImmediate.setOnClickListener {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentTimeMillis(), pendingIntent)
            this.finish()
        }
        binding.buttonGallery.setOnClickListener {
            val imageViewintent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            imageViewintent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            imageViewintent.type = "image/*"

            getContent.launch(imageViewintent)

//            val preview: ImageView = findViewById(R.id.imageView_preview)
//            preview.setImageResource(intent.data)
        }
    }
}

