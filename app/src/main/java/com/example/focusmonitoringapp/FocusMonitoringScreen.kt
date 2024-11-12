package com.example.focusmonitoringapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmonitoringapp.viewmodel.BLEState

@Composable
fun FocusMonitoringScreen(
    focusScore: Int,
    isMonitoring: Boolean,
    bleState: BLEState,
    onToggleMonitoring: () -> Unit,
    onBLEScan: () -> Unit,
    onBLEDisconnect: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // BLE Simulation UI
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ble_status, bleState.name),
                fontSize = 15.sp
            )

            when (bleState) {
                BLEState.Idle -> {
                    Button(
                        onClick = onBLEScan,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.sizeIn(minWidth = 64.dp)
                    ) {
                        Text(text = stringResource(R.string.start_ble_scan), fontSize = 15.sp)
                    }
                }
                BLEState.Scanning -> {
                    Text(text = stringResource(R.string.scanning_ble), fontSize = 15.sp)
                }
                BLEState.Connected -> {
                    Button(
                        onClick = onBLEDisconnect,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.sizeIn(minWidth = 64.dp)
                    ) {
                        Text(stringResource(R.string.disconnect_ble), fontSize = 15.sp)
                    }
                }
            }
        }

        // Data Score Simulation UI
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.focus_score),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = focusScore.toString(), fontSize = 32.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onToggleMonitoring
            ) {
                Text(
                    text = if (isMonitoring) stringResource(id = R.string.stop) else stringResource(id = R.string.start)
                )
            }
        }
    }
}
