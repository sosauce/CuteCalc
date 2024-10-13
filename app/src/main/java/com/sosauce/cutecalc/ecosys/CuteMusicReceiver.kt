package com.sosauce.cutecalc.ecosys

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CuteMusicReceiver(
    private val action: (String) -> Unit
) : BroadcastReceiver() {

    companion object {
        private const val CURRENTLY_PLAYING_CHANGED = "CM_CUR_PLAY_CHANGED"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == CURRENTLY_PLAYING_CHANGED) {

            val data = intent.getStringExtra("currentlyPlaying")

            data?.let { action(it) }

        }
    }
}