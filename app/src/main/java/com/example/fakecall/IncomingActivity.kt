package com.example.fakecall

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.ebanx.swipebtn.SwipeButton
import kotlin.system.exitProcess

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkPermission
import java.security.AccessController.checkPermission

class IncomingActivity : AppCompatActivity() {

    private lateinit var incomingAnimation: AnimationDrawable
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var textViewName: TextView

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        }
        else {
            this.window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        }

        val callerName = intent.getStringExtra("Caller Name")
        val callerImage: ImageView = findViewById(R.id.imageView_face)

        val uri = intent.getParcelableExtra<Uri>("uri")
        callerImage.setImageURI(uri)

        textViewName = findViewById(R.id.textView_name)
        textViewName.text = callerName

        rootLayout = findViewById(R.id.root_layout)
        incomingAnimation = rootLayout.background as AnimationDrawable
        incomingAnimation.setEnterFadeDuration(2000)
        incomingAnimation.setExitFadeDuration(4000)
        incomingAnimation.start()

        // swipe event answer button
        val answerButton: SwipeButton = findViewById(R.id.swipebutton_answer)
        answerButton.setOnStateChangeListener() {
            val answerIntent = Intent(this, AnswerActivity::class.java)
            answerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            answerIntent.putExtra("uri",uri)
            //answerIntent.putExtra("Caller Name", data?.getString("Caller Name"))
            this.finish()
            this.startActivity(answerIntent)
        }

        // swipe event cancel button
        val hangupButton: SwipeButton = findViewById(R.id.swipebutton_hangup)
        hangupButton.setOnStateChangeListener() {
            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }
    }
}