package com.sosauce.cutecalc.ecosys

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CuteMusicReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "CM") {
            //val curplay = intent.getStringExtra("now_playing")
            println("notcool")
        }
    }
}