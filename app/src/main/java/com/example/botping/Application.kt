package com.example.botping

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.botping.classes.ActiveDevice
import com.example.botping.data.DeviceInfo
import com.example.botping.ui.AppViewModel
import com.example.botping.ui.UiState
import com.example.botping.ui.composables.Banner
import com.example.botping.ui.composables.DeviceList
import com.example.botping.ui.composables.NetworkInfo
import com.example.botping.util.createIpObject
import com.example.botping.util.pingIpAddresses
import com.example.botping.util.subnetMaskToPrefix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.net.InetAddress
import kotlin.experimental.and
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Application(
    initDeviceInfo: DeviceInfo,
) {
    val appViewModel = AppViewModel()
    val uiState: UiState by appViewModel.uiState.collectAsState()
    appViewModel.updateDeviceInfo(initDeviceInfo)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Banner() })
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(innerPadding)
        ) {

            val deviceList = uiState.activeDevicesList
            var isScanning by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope() // Creates a coroutine scope

            //Showing DeviceInfo & networking details of wifi interface
            NetworkInfo(
                WInterfaceName = uiState.deviceInfoUI.wifiInterfaceName,
                ipAddress = uiState.deviceInfoUI.deviceIpAddress,
                netMask = uiState.deviceInfoUI.deviceNetworkNetmask,
                Modifier
                    .padding(4.dp)
            )

            DeviceList(activeDevives = deviceList, isScanning)

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(10.dp, 4.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        appViewModel.resetDeviceList()
                        isScanning = true

                        // Have to run a concurrent subroutine that will take the host ip address
                        // and subnet mask and ping all ip addresses on that network, each ip
                        // address that comes back active will update the activeDevicesList via
                        // addActiveDevice().
                        scope.launch {
                            pingIpAddresses(
                                uiState.deviceInfoUI.deviceIpAddress,
                                uiState.deviceInfoUI.deviceNetworkNetmask,
                                listUpdater = {
                                        activeDevice: ActiveDevice ->
                                    appViewModel.addActiveDevice(activeDevice)
                                }
                            )
                            isScanning = false
                        }

                    },
                    enabled = !isScanning,
                ) {
                    Text("Start Scan")
                }

                Button(
                    onClick = { // Cancel Ping coroutine scope
                        // TODO unable to cancel thread job correct, continue even after cancel signal is sent.
                        scope.cancel("Canceling coroutine scope...")
                        isScanning = false
                    },
                    enabled = isScanning,
                ) {
                    Text("Cancel Scan")
                }
            }
        }
    }
}

