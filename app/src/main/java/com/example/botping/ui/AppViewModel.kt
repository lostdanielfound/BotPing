package com.example.botping.ui

import androidx.lifecycle.ViewModel
import com.example.botping.classes.ActiveDevice
import com.example.botping.data.DeviceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    var _uiState = MutableStateFlow(UiState())
        private set

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateDeviceInfo(DeviceInfoUI: DeviceInfo) {
        _uiState.update { currentState ->
            currentState.copy(
                deviceInfoUI = DeviceInfoUI
            )
        }
    }

    fun addActiveDevice(activeDevice: ActiveDevice) {
        val currentActiveDeviceList: MutableList<ActiveDevice> = _uiState.value.activeDevicesList.toMutableList()
        currentActiveDeviceList.add(activeDevice)
        _uiState.update { currentState ->
            currentState.copy(
                activeDevicesList = currentActiveDeviceList
            )
        }
    }

    fun resetDeviceList() {
        _uiState.update { currentState ->
            currentState.copy(
                activeDevicesList = listOf<ActiveDevice>()
            )
        }
    }

}

data class UiState(
    val deviceInfoUI: DeviceInfo = DeviceInfo(),
    val activeDevicesList: List<ActiveDevice> = listOf<ActiveDevice>()
)