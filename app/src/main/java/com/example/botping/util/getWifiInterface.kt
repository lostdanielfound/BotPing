package com.example.botping.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.NetworkInterface

fun getWifiInterfaceName(context: Context): String? {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Get the active network and check if it's a Wi-Fi network
    val activeNetwork = connectivityManager.activeNetwork ?: return null
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return null

    // Check if the network is Wi-Fi
    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

        // Get all network interfaces
        val networkInterfaces: List<NetworkInterface> = NetworkInterface.getNetworkInterfaces().toList()

        // Look for the Wi-Fi interface, typically named "wlan0"
        for (networkInterface in networkInterfaces) {
            if (networkInterface.name.contains("wlan")) {
                return networkInterface.name
            }
        }
    }

    return null  // Return null if no Wi-Fi interface is found
}