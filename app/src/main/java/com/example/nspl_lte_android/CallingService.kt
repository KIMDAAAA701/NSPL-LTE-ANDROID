package com.example.nspl_lte_android

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

class CallingService : Service() {

    var phone_number : String? = null
    var callStartTime : Date? = null

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    var voiceFilePath : String = "/Call"


}// class end