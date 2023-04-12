package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1,1,1,0,0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000,1,1,0,0);

    public static LocalDateTime getStartInclusive(LocalDate start){
       return start!=null? start.atStartOfDay() : MIN_DATE;
    }
    public static LocalDateTime getEndExclusive(LocalDate end){
        return end!=null? end.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static < T extends Comparable<T>> boolean isBetweenHalfOpen(T time, @Nullable T start, @Nullable T end) {
        return (start==null || time.compareTo(start) >= 0) && (end==null || time.compareTo(end) < 0);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
            return StringUtils.hasLength(str) ? LocalDate.parse(str) : null;
    }
    public static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.hasLength(str) ? LocalTime.parse(str) : null;
        }
}
