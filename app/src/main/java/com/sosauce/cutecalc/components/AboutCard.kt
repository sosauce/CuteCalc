package com.sosauce.cutecalc.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.utils.GITHUB_RELEASES

@Composable
fun AboutCard() {

    val context = LocalContext.current
    val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
    val uriHandler = LocalUriHandler.current

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.icon2),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(100.dp)
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15))
            )
            Column {
                CuteText(stringResource(R.string.cc_by_sosauce))
                CuteText(
                    text = "${stringResource(R.string.version)} $version",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
                )
            }
        }
        Row(
            modifier = Modifier.padding(8.dp)
        ) {

            Row {
                Button(
                    onClick = { uriHandler.openUri(GITHUB_RELEASES) },
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { CuteText(stringResource(R.string.update)) }
            }
        }
    }
}