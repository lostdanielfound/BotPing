package com.example.botping.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import java.net.InetAddress
import java.net.InterfaceAddress
import java.net.NetworkInterface

fun getCurrentNetMask(context: Context): InetAddress? {
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
                try { // Catch if wifi interface has no IP addresses related to it.
                    wifiInterfaceAddress = networkInterface.interfaceAddresses.last()
                } catch (e: NoSuchElementException) {
                    Log.w("Network Details", "No IP address under interface $networkInterfaceName")
                    return null
                }

                val subnetMask = wifiInterfaceAddress.networkPrefixLength
                Log.i("Network Details", "Obtained Subnet Mask")

                return shortToSubnetMask(subnetMask)
            }
        }
    }

    Log.w("Network Details", "Couldn't obtain host IP address from Wifi Interface")
    return null
}

fun shortToSubnetMask(prefixLength: Short): InetAddress {
    // Bitwise mask creation, shift binary address on prefixLength
    val shiftedAddress = (0xFFFFFFFF shl (32 - prefixLength.toInt())).toInt()

    // Create a byte array of all the individual octets.
    val bytes = ByteArray(4) { i -> (shiftedAddress shr (24 - i * 8) and 0xFF).toByte() }

    return InetAddress.getByAddress(bytes)
}