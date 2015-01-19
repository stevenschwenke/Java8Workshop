import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Exercises {

    // 5. Streams

    // The following Test prints only random numbers greater 0.5, but it doesn't print 3 of these numbers every time.
    // Rewrite it so that it prints 3 numbers. Shorten the code as much as possible.
    @Test
    public void orderOfOperationsInStreams() {

        System.out.println("Given:");
        Stream.generate(Math::random).limit(3).filter(new Predicate<Double>() {
            @Override
            public boolean test(Double o) {
                return o > 0.5;
            }
        }).forEach((x) -> System.out.println(x));

        // Solution: Streams execute operations in the given order. Here, the random numbers are limited to 3 and
        // then filtered. To guarantee that 3 numbers are printed, they should first be filtered and then limited:
        System.out.println("Solution:");
        Stream.generate(Math::random).filter(o -> o > 0.5).limit(3).forEach((x) -> System.out.println(x));
    }

    // Write conventional Java 7 code that calculates the formula y = sum(x*x + 5) for a list of integers. The result
    // should be printed as a single integer. When you are finished, rewrite the code to Java 8 using streams and
    // lambdas.
    @Test
    public void doingMathWithLambdas() {

        List<Integer> args = new ArrayList<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));

        System.out.println("Java 7:");
        int resultJava7 = 0;
        for (Integer i : args) {
            resultJava7 += i * i + 5;
        }
        System.out.println(resultJava7);

        System.out.println("Java 8 uncool:");
        Object resultJava8Uncool = args.stream().map(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) {
                return integer * integer + 5;
            }
        }).reduce(new BinaryOperator<Object>() {
            @Override
            public Object apply(Object o, Object o2) {
                return (Integer) o + (Integer) o2;
            }
        }).get();
        System.out.println(resultJava8Uncool);

        System.out.println("Java 8:");
        Object resultJava8 = args.stream().map(integer -> integer * integer + 5).reduce((o, o2) -> o + o2).get();
        System.out.println(resultJava8);
    }

    // Write a generator that prints a number and its 100 following numbers.
    @Test
    public void generatingIntegers() {
        Stream<Integer> integerStream = Stream.generate(new AtomicInteger(5)::getAndIncrement).limit(100);
        integerStream.forEach(System.out::println);
    }

    // Advanced task: write a generator that prints the current date and the date of the next 100 days.
    @Test
    public void generatingCalendarDays() {
        Stream<Date> dateStream = Stream.iterate(new Date(), date -> {
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.set(GregorianCalendar.DAY_OF_YEAR, c.get(GregorianCalendar.DAY_OF_YEAR) + 1);
            return c.getTime();
        });
        dateStream.limit(100).forEach(System.out::println);
    }
}
