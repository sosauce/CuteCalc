package com.sosauce.cutecalc.ecosys

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EcosystemViewModel: ViewModel() {

    var currentlyPlaying by mutableStateOf<String?>(null)

//    inner class CuteMusic {
//        var currentlyPlaying by mutableStateOf<String?>(null)
//    }


}