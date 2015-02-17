import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Exercises for chapter 4 and 5: Streams.
 */
public class C_05_Streams_Exercises {

    // Exercise 1 - orderOfOperationsInStreams
    // The following code should print 3 random numbers that each are greater than 0.5. However,
    // it doesn't print 3 of these numbers every time. Sometimes there are only two or one.
    // Rewrite the code so that it prints 3 numbers. Shorten the code as much as possible.
    @Test
    public void orderOfOperationsInStreams() {

        System.out.println("Given:");
        Stream.generate(Math::random).limit(3).filter(new Predicate<Double>() {
            @Override
            public boolean test(Double o) {
                return o > 0.5;
            }
        }).forEach((x) -> System.out.println(x));
    }

    // Exercise 2 - doingMathWithLambdas
    // Write conventional Java 7 code that calculates the formula y = sum(x*x + 5) for a list of integers. The result
    // should be printed as a single integer. When you are finished, rewrite the code to Java 8 using streams and
    // lambdas.

    // Exercise 3 - generatingIntegers
    // Write a generator that prints a number and its 100 following numbers.

    // Exercise 4 - generatingCalendarDays
    // Advanced task: write a generator that prints the current date and the date of the next 100 days.

    // Exercise 5 - reduceWithRobustErrorHandling
    // Write code that accepts a list/array of integers. Only the even numbers shall be printed out and summed. The sum
    // should also be printed.

    // Exercise 6 - showThatParallelStreamRunsOnDifferentThreads
    // Write code that shows how sequential streams run on one thread and parallel streams run on multiple threads.

    ////////////////////////
    // SOLUTIONS
    ////////////////////////

    @Test
    public void orderOfOperationsInStreamsSOLUTION() {

        // Solution: Streams execute operations in the given order. Here, the random numbers are limited to 3 and
        // then filtered. To guarantee that 3 numbers are printed, they should first be filtered and then limited:
        System.out.println("Solution:");
        Stream.generate(Math::random).filter(o -> o > 0.5).limit(3).forEach((x) -> System.out.println(x));
    }

    @Test
    public void doingMathWithLambdasSOLUTION() {

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

    @Test
    public void generatingIntegersSOLUTION() {
        Stream<Integer> integerStream = Stream.generate(new AtomicInteger(5)::getAndIncrement).limit(100);
        integerStream.forEach(System.out::println);
    }

    @Test
    public void generatingCalendarDaysSOLUTION() {
        Stream<Date> dateStream = Stream.iterate(new Date(), date -> {
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.set(GregorianCalendar.DAY_OF_YEAR, c.get(GregorianCalendar.DAY_OF_YEAR) + 1);
            return c.getTime();
        });
        dateStream.limit(100).forEach(System.out::println);
    }

    @Test
    public void reduceWithRobustErrorHandlingSOLUTION() {
        Integer[] integers = {1, 2, 3, 4, 5};

        Stream<Integer> stream = Stream.of(integers).filter(integer -> integer % 2 == 0).peek(System.out::println);
        Optional<Integer> reduce = stream.reduce(Integer::sum);

        // Ooops - this could throw a nasty exception if there are no values in the list any more.
        System.out.println(reduce.get());

        // This is pretty robust and won't throw exceptions.
        System.out.println(reduce.orElse(0));
    }

    @Test
    public void showThatParallelStreamRunsOnDifferentThreadsSOLUTION() {
        System.out.println("Sequential:");
        Stream<Integer> seqStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        seqStream.forEach(i -> {
            System.out.println(Thread.currentThread().getName() + ": i");
        });

        System.out.println("Parallel:");
        Stream<Integer> parallelStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).parallel();
        parallelStream.forEach(i -> {
            System.out.println(Thread.currentThread().getName() + ": i");
        });

    }

    // TODO: ForkJoinPool standardmäßig anzahl Processor. Kann konfiguriert werden:
    //   ForkJoinPool forkJoinPool = new ForkJoinPool(2);
}
