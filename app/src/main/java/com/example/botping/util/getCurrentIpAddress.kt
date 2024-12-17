package com.example.botping.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.net.InetAddress
import java.net.InterfaceAddress
import java.net.NetworkInterface

fun getCurrentIpAddress(context: Context): InetAddress? {

    //Getting WIFImanager from system context
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork ?: return null
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return null

    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

        // Get all network interfaces
        val networkInterfaces: List<NetworkInterface> = NetworkInterface.getNetworkInterfaces().toList()

        // Look for the Wi-Fi interface, typically named "wlan0"
        for (networkInterface in networkInterfaces) {
            val networkInterfaceName = networkInterface.name
            if (networkInterfaceName.contains("wlan")) {
                // Look through ip address related to interface
                val wifiInterfaceAddress: InterfaceAddress
                try {
                    wifiInterfaceAddress = networkInterface.interfaceAddresses.last()
                } catch (e: NoSuchElementException) {
                    Log.w("Network Details", "No IP address under interface $networkInterfaceName")
                    return null
                }

                val rawIP = wifiInterfaceAddress.address
                Log.i("Network Details", "Obtained Host IP address")

                return rawIP
            }
        }
    }

    Log.w("Network Details", "Couldn't obtain host IP address from Wifi Interface")
    return null
}