package com.example.botping.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.botping.classes.ActiveDevice
import com.example.botping.util.createIpObject

@Composable
fun DeviceList(
    activeDevives: List<ActiveDevice>,
    isScanning: Boolean,
    modifier: Modifier = Modifier
) {
    val itemPaddingX = 14.dp
    val itemPaddingY = 8.dp

    Surface {
        Card(
            border = BorderStroke(2.dp, Color.Black),
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .heightIn(
                    min = 76.dp,
                    max = 150.dp
                )
        ) {
            Box(
            ) {
                if (isScanning) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(itemPaddingX, itemPaddingY),
                ) {
                    items(
                        items = activeDevives,
                    ) { deviceEntry ->
                        DeviceEntry(deviceEntry)
                        Divider(Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceEntry(deviceIP: ActiveDevice, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = "IP address: ${deviceIP.ipv4Address.hostAddress}")
    }
}

@Preview
@Composable
fun DeviceListPreview() {
    val testAddressList = mutableListOf<ActiveDevice>(
        ActiveDevice(createIpObject(10,0,0,2)),
        ActiveDevice(createIpObject(10,0,0,3)),
        ActiveDevice(createIpObject(10,0,0,4)),
        ActiveDevice(createIpObject(10,0,0,5)),
        ActiveDevice(createIpObject(10,0,0,6)),
        ActiveDevice(createIpObject(10,0,0,7)),
        ActiveDevice(createIpObject(10,0,0,8)),
    )
    
    DeviceList(activeDevives = testAddressList, false)
}