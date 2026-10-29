package com.lifepilot.personal

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalTime
import java.time.Duration

class ScheduleCalculatorTest {

    private fun calculatePlannedMinutes(start: LocalTime, end: LocalTime): Long {
        val duration = Duration.between(start, end)
        return if (duration.isNegative) {
            duration.plusDays(1).toMinutes()
        } else {
            duration.toMinutes()
        }
    }

    @Test
    fun standardDayActivity_calculatesAccurately() {
        val start = LocalTime.of(9, 0)
        val end = LocalTime.of(17, 30)
        assertEquals(510L, calculatePlannedMinutes(start, end)) // 8.5 hours = 510 min
    }

    @Test
    fun midnightCrossingSleepBlock_calculatesAccurately() {
        val start = LocalTime.of(23, 0)
        val end = LocalTime.of(7, 30)
        assertEquals(510L, calculatePlannedMinutes(start, end)) // 8.5 hours overnight
    }
}