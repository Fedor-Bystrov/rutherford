package app.rutherford.core.util

import java.time.Instant
import java.time.ZoneOffset.UTC

object Clock {
    private val clock: java.time.Clock by lazy { java.time.Clock.tickMillis(UTC) }

    fun now(): Instant {
        return clock.instant()
    }
}