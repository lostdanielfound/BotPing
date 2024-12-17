package com.example.botping

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.botping.data.DeviceInfo
import com.example.botping.ui.theme.BotPingTheme
import com.example.botping.util.createIpObject
import com.example.botping.util.getCurrentIpAddress
import com.example.botping.util.getCurrentNetMask
import com.example.botping.util.getWifiInterfaceName
import java.net.InetAddress

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BotPingTheme {

                //obtaining DeviceInfo details related to the current system context
                /* TODO: The viewmodel will update the UI after every change to the UIstate thus will run these functions again... */
                val wifiInterfaceName = getWifiInterfaceName(this) ?: ""
                val currentIpAddress: InetAddress
                    = getCurrentIpAddress(this) ?: createIpObject(0,0,0,0)
                val currentNetMask: InetAddress
                    = getCurrentNetMask(this) ?: createIpObject(0,0,0,0)

                Application(
                    initDeviceInfo = DeviceInfo(
                        wifiInterfaceName,
                        currentIpAddress,
                        currentNetMask,
                    ),
                )
            }
        }
    }

    private fun checkPermissionFor(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}