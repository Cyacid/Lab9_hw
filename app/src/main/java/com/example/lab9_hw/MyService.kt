package com.example.lab9_hw

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

class MyService : Service() {
    companion object {
        var flag: Boolean = false
    }
    private var hour = 0
    private var minute = 0
    private var second = 0

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startID: Int): Int {
        flag = intent.getBooleanExtra("flag", false)

        object : Thread() {
            override fun run() {
                while (flag) {
                    try {
                        sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    //計數器加一
                    second++
                    if (second >= 60) {
                        second = 0
                        minute++
                        //分鐘數大於60進位
                        if (minute >= 60) {
                            minute = 0
                            hour++
                        }
                    }
                    val intent = Intent("MyMessage")
                    val bundle = Bundle()
                    bundle.putInt("H", hour)
                    bundle.putInt("M", minute)
                    bundle.putInt("S", second)
                    sendBroadcast(intent.putExtras(bundle))
                }
            }
        }.start()
        return START_STICKY
    }
}
