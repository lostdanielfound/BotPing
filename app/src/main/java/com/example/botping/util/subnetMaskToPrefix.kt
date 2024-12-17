package com.example.botping.util

import java.net.InetAddress
import android.util.Log

fun subnetMaskToPrefix(subnetMaskIpAddress: InetAddress): Int {
    val bytes = subnetMaskIpAddress.address

    val rawBinaryAddress = (bytes[0].toInt() shl 24) or
            (bytes[1].toInt() shl 16) or
            (bytes[2].toInt() shl 8) or
            bytes[3].toInt()

    // Count the number of 1 bits in the binary representation
    return Integer.bitCount(rawBinaryAddress)
}
