package com.example.botping.util

import android.util.Log
import com.example.botping.classes.ActiveDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.net.InetAddress
import kotlin.experimental.and
import kotlin.math.pow

suspend fun pingIpAddresses(
    hostIpAddress: InetAddress,
    subnetMaskIpAddress: InetAddress,
    listUpdater: (ActiveDevice) -> Unit
) {

    //Constructing subnet mask as ByteArray
    val subnetAddress = ByteArray(4) { i ->
        hostIpAddress.address[i] and subnetMaskIpAddress.address[i]
    }

    //Creating a list of addresses to attempt to ping
    val numberOfAddresses = 2.0.pow((32 - subnetMaskToPrefix(subnetMaskIpAddress))).toInt() - 2
    val networkAddresses = buildList<InetAddress> {
        for (i in 1..numberOfAddresses) {
            if (i == 1) { //Skip the first address (Network address)
                continue
            }
            var newAddress = BigInteger(1, subnetAddress)
            newAddress += i.toBigInteger()
            add(InetAddress.getByAddress(newAddress.toByteArray()))
        }
    }

    Log.i("Network Details", "Total number of possible address: ${networkAddresses.size}")

    withContext(Dispatchers.IO) {
        for (networkAddress in networkAddresses) {
            if (networkAddress.isReachable(200)) {
                listUpdater(ActiveDevice(networkAddress))
            } else {
                Log.w("Network Details", "Failed to ping address ${networkAddress.hostAddress}")
            }
        }
    }

}
