package components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import com.sosauce.cutecalc.logic.getAmoledModeSetting
import com.sosauce.cutecalc.logic.getButtonVibrationSetting
import com.sosauce.cutecalc.logic.getDarkModeSetting
import com.sosauce.cutecalc.logic.getDecimalFormattingSetting
import com.sosauce.cutecalc.logic.saveAmoledModeSetting
import com.sosauce.cutecalc.logic.saveButtonVibrationSetting
import com.sosauce.cutecalc.logic.saveDarkModeSetting
import com.sosauce.cutecalc.logic.saveDecimalFormattingSetting
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
            modifier = Modifier.padding(10.dp)
        )
        Text(text = "Haptic Feedback", fontFamily = GlobalFont)
    }

}

@Composable
fun DecimalSwitch() {
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val decimalSetting by getDecimalFormattingSetting(dataStore).collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = decimalSetting,
            onCheckedChange = { isChecked ->
                coroutineScope.launch {
                    saveDecimalFormattingSetting(dataStore, isChecked)
                }
            },
            modifier = Modifier.padding(10.dp)
        )
        Text(text = "Decimal Formatting", fontFamily = GlobalFont)
    }

}

@Composable
fun Switches() {

    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val decimalSetting by getDecimalFormattingSetting(dataStore).collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    val buttonVibrationSetting by getButtonVibrationSetting(dataStore).collectAsState(initial = false)

    Column {
        Text(text = "Misc", fontFamily = GlobalFont, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp))

        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomStart = 4.dp,
                bottomEnd = 4.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Decimal Formatting",
                    fontFamily = GlobalFont
                )

                Switch(
                    checked = decimalSetting,
                    onCheckedChange = { isChecked ->
                        coroutineScope.launch {
                            saveDecimalFormattingSetting(dataStore, isChecked)
                        }
                    }
                )
            }
        }
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomStart = 24.dp,
                bottomEnd = 24.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Haptic Feedback",
                    fontFamily = GlobalFont
                )

                Switch(
                    checked = buttonVibrationSetting,
                    onCheckedChange = { isChecked ->
                        // Update the button vibration setting when Switch state changes
                        coroutineScope.launch {
                            saveButtonVibrationSetting(dataStore, isChecked)
                        }
                    }
                )
            }
        }
    }


}

@Composable
fun ThemeManagement() {
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val darkMode by getDarkModeSetting(dataStore).collectAsState(initial = isSystemInDarkTheme())
    val amoled by getAmoledModeSetting(dataStore).collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text(text = "Theme", fontFamily = GlobalFont, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp))
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomStart = 4.dp,
                bottomEnd = 4.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Dark Mode",
                    fontFamily = GlobalFont
                )
                Switch(
                    checked = darkMode,
                    onCheckedChange = { isChecked ->
                        // Update the setting when Switch state changes
                        coroutineScope.launch {
                            saveDarkModeSetting(dataStore, isChecked)

                        }
                    }
                )
            }
        }
    }
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomStart = 24.dp,
            bottomEnd = 24.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Amoled Mode",
                fontFamily = GlobalFont
            )
            Switch(
                checked = amoled,
                onCheckedChange = { isChecked ->
                    // Update the setting when Switch state changes
                    coroutineScope.launch {
                        saveAmoledModeSetting(dataStore, isChecked)
                    }
                }
            )
        }
    }
}







