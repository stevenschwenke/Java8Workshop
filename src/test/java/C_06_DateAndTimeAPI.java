import org.junit.Test;

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Because of several problems, there are alternatives to the old java.util.Date and java.util.Calendar classes.
 */
public class C_06_DateAndTimeAPI {

    /*
        Objects of type Date and Calendar are mutable, there always are setters. This causes them to be not thread-safe.

        new API: concepts of date and time are separated:
         java.time.Instant = point in time, represented in milliseconds. Good for machines, bad for humans.
         java.time.LocalDate = date, java.time.LocalTime = time - both human-readable.
     */

    @Test
    public void representingMyBirthdayIndependentFromYear() {

        // Java 7: always in a specific year:
        GregorianCalendar d1 = new GregorianCalendar();
        d1.set(GregorianCalendar.YEAR, 2015);
        d1.set(GregorianCalendar.MONTH, 7);
        d1.set(GregorianCalendar.DAY_OF_MONTH, 19);
        Date stevensBirthdayIn2015 = d1.getTime();

        // Java 8: Stevens birthday in every year:
        MonthDay d2 = MonthDay.of(7, 19);

        // ... can be used in every year:
        LocalDate localDate = d2.atYear(2015);
        System.out.println(localDate.getDayOfWeek());
    }

    @Test
    public void dateAndTimeObjectsAreImmutable() {
        LocalDate birthday = LocalDate.of(2015, 7, 19);
        // No setter methods to change LocalDate! Only possible to create new objects like this:
        LocalDate birthdayIn2016 = birthday.withYear(2016);

        // Hence, objects are thread-safe.
    }

    @Test
    public void calculatingTimeAndDate() {

        // Simplified methods for calculating time and date and doing math:

        LocalTime now = LocalTime.now();
        System.out.println(now);
        LocalTime beforeTwoHours = now.minusHours(2);
        System.out.println(beforeTwoHours);
        LocalTime inTwoHours = now.plusHours(2);
        System.out.println(inTwoHours);
        LocalTime withOneOClock = now.withHour(1);
        System.out.println(withOneOClock);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDate beforeTwoDays = localDate.minusDays(2);
        System.out.println(beforeTwoDays);
        LocalDate inTwoDays = localDate.plusDays(2);
        System.out.println(inTwoDays);
        LocalDate withFirstDayOfMonth = localDate.withDayOfMonth(1);
        System.out.println(withFirstDayOfMonth);
    }

    @Test
    public void temporalAdjuster() {
        // temporal adjuster = implementation of strategy design pattern for modifying a temporal object

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Last day of year: " + now.with(TemporalAdjusters.lastDayOfYear()));
        System.out.println("First day of next year: " + now.with(TemporalAdjusters.firstDayOfNextYear()));

        TemporalAdjuster nextOddDayTemporalAdjuster = new TemporalAdjuster() {
            @Override
            public Temporal adjustInto(Temporal temporal) {
                LocalDate localDate = LocalDate.from(temporal);

                int day = localDate.getDayOfMonth();
                if (day % 2 == 0) {
                    localDate = localDate.plusDays(1);
                } else {
                    localDate = localDate.plusDays(2);
                }

                return temporal.with(localDate);
            }
        };

        System.out.println("Next odd day: " + now.with(nextOddDayTemporalAdjuster));
    }

}
