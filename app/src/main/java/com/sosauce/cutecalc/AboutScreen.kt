@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import com.sosauce.cutecalc.ui.theme.GlobalFont
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavController, viewModel: AppVersion) {

    val iconsTint = MaterialTheme.colorScheme.onBackground
    val urlList = listOf("https://github.com/sosauce/CuteCalc/issues", "https://github.com/sosauce/CuteCalc/", "https://github.com/sosauce/CuteCalc/releases")
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(title = "About", showBackArrow = true, navController = navController, showMenuIcon = false)
        },
    ) {
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) { Text(text = "Github", fontSize = 20.sp, fontFamily = GlobalFont, color = iconsTint) }

                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable() {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlList[2]))

                            context.startActivity(intent)

                        }
                ) {
                    Icon(Icons.Default.Update, contentDescription = null, modifier = Modifier.size(30.dp), tint = iconsTint)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Check for updates", fontSize = 25.sp, fontFamily = GlobalFont, color = iconsTint)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlList[1]))
                            context.startActivity(intent)
                        }
                ) {
                    Icon(Icons.Outlined.StarOutline, contentDescription = null, modifier = Modifier.size(30.dp), tint = iconsTint)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Star the project", fontSize = 25.sp, fontFamily = GlobalFont, color = iconsTint)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlList[0]))
                            context.startActivity(intent)
                        }
                ) {
                    Icon(Icons.Outlined.BugReport, contentDescription = null, modifier = Modifier.size(30.dp), tint = iconsTint)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Report a bug", fontSize = 25.sp, fontFamily = GlobalFont, color = iconsTint)
                }
            }
            Text(text = "Made with ❤️ by sosauce", fontFamily = GlobalFont, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
        }
    }


class AppVersion(application: Application) : AndroidViewModel(application) {
    val versionName: String by lazy {
        val packageInfo = application.packageManager.getPackageInfo(application.packageName, 0)
        packageInfo.versionName
    }
}
