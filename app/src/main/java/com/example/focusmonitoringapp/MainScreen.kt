package com.example.focusmonitoringapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.focusmonitoringapp.viewmodel.BLEState
import com.example.focusmonitoringapp.viewmodel.FocusViewModel

@Composable
fun MainScreen(viewModel: FocusViewModel = viewModel()) {
    val focusScore by viewModel.focusScore.collectAsState(initial = 0)
    val isMonitoring by viewModel.isMonitoring.collectAsState(initial = false)
    val bleState by viewModel.bleState.collectAsState(initial = BLEState.Idle)

    FocusMonitoringScreen(
        focusScore = focusScore,
        isMonitoring = isMonitoring,
        bleState = bleState,
        onToggleMonitoring = { viewModel.toggleMonitoring() },
        onBLEScan = { viewModel.startBLEScan() },
        onBLEDisconnect = { viewModel.disconnectBLE() }
    )
}
