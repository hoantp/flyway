/*-
 * ========================LICENSE_START=================================
 * flyway-core
 * ========================================================================
 * Copyright (C) 2010 - 2025 Red Gate Software Ltd
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package org.flywaydb.core.internal.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.time.temporal.ChronoUnit.DAYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    /**
     * Formats this date in the standard ISO yyyy-MM-dd HH:mm:ss format.
     *
     * @param date The date to format.
     *
     * @return The date in ISO format. An empty string if the date is null.
     */
    public static String formatDateAsIsoString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatStringAsIsoDateString(String date) {
        if (date == null) {
            return "";
        }
        
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date))));
    }

    /**
     * Formats the time of this date in the standard ISO HH:mm:ss format.
     *
     * @param date The date to format.
     *
     * @return The time in ISO format. An empty string if the time is null.
     */
    public static String formatTimeAsIsoString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * Create a new date with this year, month and day.
     *
     * @param year  The year.
     * @param month The month (1-12).
     * @param day   The day (1-31).
     *
     * @return The date.
     */
    public static Date toDate(int year, int month, int day) {
        return new GregorianCalendar(year, month - 1, day).getTime();
    }

    /**
     * Converts this date into a YYYY-MM-dd string.
     *
     * @param date The date.
     *
     * @return The matching string.
     */
    public static String toDateString(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String year = "" + calendar.get(Calendar.YEAR);
        String month = StringUtils.trimOrLeftPad("" + (calendar.get(Calendar.MONTH) + 1), 2, '0');
        String day = StringUtils.trimOrLeftPad("" + calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
        return year + "-" + month + "-" + day;
    }

    public static Date addDaysToDate(Date fromDate, int days) {
        return Date.from(fromDate.toInstant()
                                 .atZone(ZoneId.systemDefault())
                                 .toLocalDate()
                                 .plusDays(days)
                                 .atStartOfDay(ZoneId.systemDefault())
                                 .toInstant());
    }

    public static long getRemainingDays(Date expiry) {
        return DAYS.between(new Date().toInstant(), expiry.toInstant());
    }

}
