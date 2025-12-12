package com.leon.springbootleon.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class DateUtils {

    /** 預設字串樣式：2024-01-01 00:00:00 */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final ZoneId ASIA_TAIPEI = ZoneId.of("Asia/Taipei");

    /** 執行緒安全，可重用 */
    public static final DateTimeFormatter DEFAULT_FMT = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /** 將 Instant 依指定時區格式化成 yyyy-MM-dd HH:mm:ss */
    public static String format(Instant instant, ZoneId zone) {
        if (instant == null) {
            return null;
        }
        return DEFAULT_FMT.withZone(zone).format(instant);
    }

    /**
     * 民國年轉換為西元年
     * @param roc 114/1/1
     * @return String 2025-01-01
     */
    public static String rocToIso(String roc) {
        return Pattern.compile("^(\\d+)/(\\d+)/(\\d+)$")
                .matcher(roc.strip())
                .results()
                .findFirst()
                .map(m -> LocalDate.of(Integer.parseInt(m.group(1)) + 1911,
                        Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3))))
                .map(DateTimeFormatter.ISO_LOCAL_DATE::format)
                .orElse(roc);
    }

}
