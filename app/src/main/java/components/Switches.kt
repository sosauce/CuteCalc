package components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sosauce.cutecalc.logic.dataStore
import com.sosauce.cutecalc.logic.getButtonVibrationSetting
import com.sosauce.cutecalc.logic.saveButtonVibrationSetting
import com.sosauce.cutecalc.ui.theme.GlobalFont
import kotlinx.coroutines.launch

@Composable
fun VibrationSwitch() {
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val buttonVibrationSetting by getButtonVibrationSetting(dataStore).collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = buttonVibrationSetting,
            onCheckedChange = { isChecked ->
                // Update the button vibration setting when Switch state changes
                coroutineScope.launch {
                    saveButtonVibrationSetting(dataStore, isChecked)
                }
            },
            modifier = Modifier.padding(16.dp)
        )
        Text(text = "Haptic Feedback", fontFamily = GlobalFont)
    }

}

