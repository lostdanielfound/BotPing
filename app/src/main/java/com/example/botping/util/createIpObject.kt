package com.example.botping.util

import java.net.InetAddress

/**
 * The behavior of this method must enforce that only integers / byte values are allowed
 * to be passed in as octets parameters. Types of greater than 4 bytes must not be allowed and
 * values greater or less than 255 - 0 respectively can't be accepted.
 *
 * @param octet1 The first octet of the IP address
 * @param octet2 The second octet of the IP address
 * @param octet3 The third octet of the IP address
 * @param octet4 The fourth octet of the IP address
 *
 * @return InetAddress A newly created Java.Net.InetAddress object containing the IP address
*/
fun <T : Int> createIpObject(
    octet1: T,
    octet2: T,
    octet3: T,
    octet4: T
): InetAddress {
    return InetAddress.getByAddress(
        byteArrayOf(
            octet1.toByte(),
            octet2.toByte(),
            octet3.toByte(),
            octet4.toByte()
        )
    )
}