package com.example.botping.data

import com.example.botping.util.createIpObject
import java.net.InetAddress

data class DeviceInfo(
    val wifiInterfaceName: String = "",
    val deviceIpAddress: InetAddress = createIpObject(0,0,0,0),
    val deviceNetworkNetmask: InetAddress = createIpObject(0,0,0,0),
    val deviceNetworkGateway: InetAddress = createIpObject(0,0,0,0)
)
