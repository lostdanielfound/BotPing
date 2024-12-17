package com.example.botping.ui.composables

import android.net.InetAddresses
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.botping.util.createIpObject
import java.io.Console
import java.net.InetAddress

@Composable
fun NetworkInfo(
    WInterfaceName: String,
    ipAddress: InetAddress,
    netMask: InetAddress,
    modifier: Modifier = Modifier
) {
    Surface {
        Card(
            shape = CardDefaults.outlinedShape,
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            border = CardDefaults.outlinedCardBorder(),
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .border(BorderStroke(2.dp, Color.Black), ShapeDefaults.Medium)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Wifi Interface: $WInterfaceName")
                Text("\tCurrent Ip address: ${ipAddress.hostAddress}")
                Text("\tNetmask: ${netMask.hostAddress}")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun NetworkInfoPreview() {

    val wInterfaceName = "wlan0"
    val ipAddress: InetAddress = createIpObject(10,0,0,4)
    val netMask: InetAddress = createIpObject(255,255,255,255)

    NetworkInfo(
        wInterfaceName,
        ipAddress,
        netMask,
    )
}