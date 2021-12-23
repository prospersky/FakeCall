package com.example.fakecall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        var toast = Toast.makeText(context, "good", Toast.LENGTH_LONG)
//        toast.show()

        val data = intent.getBundleExtra("bundle")
//        println("image get " + data?.getParcelable("image"))

        val incomingIntent = Intent(context, IncomingActivity::class.java)
        incomingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        incomingIntent.putExtra("Caller Name", data?.getString("Caller Name"))
        incomingIntent.putExtra("image", data?.getByteArray("image"))
        incomingIntent.putExtra("uri", data?.getParcelable<Uri>("uri"))
        println("uri : " + data?.getParcelable("uri"))

        context.startActivity(incomingIntent)
    }
}