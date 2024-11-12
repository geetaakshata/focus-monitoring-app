package com.example.focusmonitoringapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

enum class BLEState {
    Idle, Scanning, Connected
}

class FocusViewModel : ViewModel() {

    private val _focusScore = MutableStateFlow(0)
    val focusScore: StateFlow<Int> get() = _focusScore

    private val _isMonitoring = MutableStateFlow(false)
    val isMonitoring: StateFlow<Boolean> get() = _isMonitoring

    private val _bleState = MutableStateFlow(BLEState.Idle)
    val bleState: StateFlow<BLEState> = _bleState

    private var monitoringJob: Job? = null

    fun toggleMonitoring() {
        if (_isMonitoring.value) {
            stopMonitoring()
        } else {
            startMonitoring()
        }
    }

    private fun startMonitoring() {
        _isMonitoring.value = true
        monitoringJob = viewModelScope.launch(Dispatchers.IO) {
            while (_isMonitoring.value) {
                val newScore = Random.nextInt(0, 100)
                withContext(Dispatchers.Main) {
                    _focusScore.value = newScore
                }
                // Simulate 5 seconds delay between each update
                delay(5000L)
            }
        }
    }

    private fun stopMonitoring() {
        _isMonitoring.value = false
        _focusScore.value = 0
        monitoringJob?.cancel()
    }

    fun startBLEScan() {
        _bleState.value = BLEState.Scanning
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)

            withContext(Dispatchers.Main) {
                _bleState.value = BLEState.Connected
            }
        }
    }

    fun disconnectBLE() {
        _bleState.value = BLEState.Idle
    }
}
