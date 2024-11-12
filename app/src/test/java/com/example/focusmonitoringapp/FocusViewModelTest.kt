package com.example.focusmonitoringapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import com.example.focusmonitoringapp.viewmodel.FocusViewModel

@ExperimentalCoroutinesApi
class FocusViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FocusViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = FocusViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `focus score updates every 5 seconds and resets on stop`() = runTest {
        // Start monitoring
        viewModel.toggleMonitoring()

        // Advance time by 5 seconds to simulate the first update
        advanceTimeBy(5000L)
        val firstFocusScore = viewModel.focusScore.first()

        // Verify the first update
        assert(firstFocusScore in 0..100)

        // Advance time by another 5 seconds for second update
        advanceTimeBy(5000L)
        val secondFocusScore = viewModel.focusScore.first()

        assert(secondFocusScore != firstFocusScore)

        // Stop monitoring
        viewModel.toggleMonitoring()

        val resetFocusScore = viewModel.focusScore.first()
        assertEquals(0, resetFocusScore)
    }
}
