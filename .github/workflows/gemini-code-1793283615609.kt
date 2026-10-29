package com.lifepilot.personal

import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressiveOverloadTest {

    private fun suggestNextWeight(lastWeight: Float, repsAchieved: Int, targetReps: Int): Float {
        return if (repsAchieved >= targetReps + 2) {
            lastWeight + 2.5f // Standard incremental load for compound movement
        } else {
            lastWeight
        }
    }

    @Test
    fun whenRepTargetExceeded_suggestsMicroload() {
        val nextLoad = suggestNextWeight(lastWeight = 80.0f, repsAchieved = 10, targetReps = 8)
        assertEquals(82.5f, nextLoad, 0.01f)
    }

    @Test
    fun whenRepTargetNotExceeded_maintainsLoad() {
        val nextLoad = suggestNextWeight(lastWeight = 80.0f, repsAchieved = 7, targetReps = 8)
        assertEquals(80.0f, nextLoad, 0.01f)
    }
}