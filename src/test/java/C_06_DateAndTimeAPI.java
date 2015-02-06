import org.junit.Test;

import java.time.LocalDate;
import java.time.MonthDay;
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

}
