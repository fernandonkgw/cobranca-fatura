package com.fnaka.cobrancafatura.domain.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    private InstantUtils() {}

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public static long between(Instant startedAt, Instant finishedAt) {
        return Duration.between(startedAt, finishedAt).toMillis();
    }

    public static long durationUntilNow(Instant startedAt) {
        return between(startedAt, InstantUtils.now());
    }
}
