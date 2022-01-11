package com.example.nspl_lte_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.telephony.TelephonyManager
import java.util.*

open class CallBroadcastReceiver : BroadcastReceiver() {

    var lastState = TelephonyManager.CALL_STATE_IDLE
    var callStartTime : Date? = null
    var isIncoming : Boolean?  = null
    var savedNumber : String? = null


    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {
            var stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            var number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            var state = 0

            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE
            }
            else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK
            }
            else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING
            }

            onCallStateChanged(context, state, savedNumber)
        }//if end
    }//onReceive() end


    //수신 전화 - 벨이 울리면 IDLE에서 RINGING으로, 응답하면 OFFHOOK으로, 전화를 끊으면 IDLE로 전환
    private fun onCallStateChanged(context: Context?, state: Int, number: String?) {
        if(lastState == state){
            // 전화 상태가 변하지 않았음
            return;
        }// if end

        when (state) {
            // 전화 왔음
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                savedNumber = number

                if (context != null) {
                    // 서비스 컴포넌트(백그라운드 작업)를 실행
                    val CallingServiceIntent = Intent(context, CallingService::class.java)
                    CallingServiceIntent.putExtra("phone_number", number.toString())
                    context.startService(CallingServiceIntent)
                }

            } // CALL_STATE_RINGING end

            // 전화 받음
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false
                    callStartTime = Date()

                    if (context != null) {
                        // 서비스 컴포넌트(백그라운드 작업)를 실행
                        val CallingServiceIntent = Intent(context, CallingService::class.java)
                        CallingServiceIntent.putExtra("phone_number", number.toString())
                        CallingServiceIntent.putExtra("callStartTime", callStartTime)
                        context.startService(CallingServiceIntent)
                    }

                }
            }// CALL_STATE_OFFHOOK end

        } // when(state) end

        lastState = state;
    }// onCallStateChanged() end
    // 출처: https://stackoverflow.com/questions/15563921/how-to-detect-incoming-calls-in-an-android-device

}//class end