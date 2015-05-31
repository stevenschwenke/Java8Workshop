package de.stevenschwenke.java.java8workshop;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.*;

import static org.junit.Assert.assertTrue;

/**
 * Stream is not a data structure, it doesn't hold data. It just holds references to the underlying stream
 * source and knows a number of tasks that should be executed against each element of the stream source. Streams are
 * pipelines that handle data structures to operations.
 * <p>
 * Streams = intermediate operations (filter, map) + terminal operation (reduce, sum)
 */
public class C_04_Streams {

    @Test
    public void creatingStreams() {

        // 1. collection-based
        String[] stringArray = {"first", "second", "third", "fourth"};
        List<String> stringList = Arrays.asList(stringArray);

        // stream = "do with every element in the given order"
        // parallel stream = "do with every element in a random order but much faster"
        Stream<String> streamFromCollection = stringList.stream();
        Stream<String> parallelStreamFromCollection = stringList.parallelStream();

        // 2. array-based
        Stream<String> streamFromArray = Arrays.stream(stringArray);
        Stream<String> parallelStreamFromArray = Arrays.stream(stringArray).parallel();

        Stream<String> streamFromStaticArrayMethod = Stream.of("first", "second", "third", "fourth");

        // 3.1 generate via supplier
        Stream<Double> randomNumberStream = Stream.generate(Math::random);
        Stream<Integer> integerStream1 = Stream.generate(new AtomicInteger()::getAndIncrement);

        // 3.2 generate via seed + operator
        Stream<Integer> integerStream2 = Stream.iterate(0, integer1 -> integer1 + 1);
    }

    @Test
    public void neverEndingStream() {

        // Supplier is a new functional interface that somehow generates values or objects.
        Supplier<Double> r = Math::random;

        // This stream has no intermediate operations and one terminal operation (println):
        Stream.generate(r).forEach(System.out::println);

        // System.out::println is a terminal operation. That's why the streams in the test above are not executed but
        // this stream here is.
    }

    @Test
    public void intermediateAndTerminalOperation() {

        // This stream has two intermediate operations and one terminal operation (println):
        System.out.println("First stream:");
        Stream<Double> stream1 = Stream.generate(Math::random);
        stream1.limit(3).sorted().forEach(System.out::println);
        stream1.close();

        // That is the same as above with a lambda expression:
        System.out.println("\n2nd stream:");
        Stream<Double> stream2 = Stream.generate(Math::random);
        stream2.limit(3).sorted().forEach((x) -> System.out.println(x));
        stream2.close();
    }

    @Test
    public void primitiveStreams() {

        // There are special streams for some primitive types: int, long and double.
        // Streams more efficient than Stream<T> because boxing/unboxing not done.
        // For int and long there are Special range-methods:
        IntStream efficientIntStream = IntStream.range(0, 4);
        Stream<Integer> inefficientIntStream = Stream.of(0, 1, 2, 3);

        LongStream efficientLongStream = LongStream.range(0L, 4L);
        Stream<Long> inefficientLongStream = Stream.of(0L, 1L, 2L, 3L);

        DoubleStream doubleStream = DoubleStream.of(0.0d, 0.5d);

        // (Streams intentionally not closed here)
    }

    @Test
    public void regexStreams() {
        String string = "This is just a random test string!";
        Stream<String> stringStream = Pattern.compile("\\W").splitAsStream(string);
        stringStream.forEach(System.out::println);
        stringStream.close();
    }

    @Test
    public void collect() {
        Stream<String> stream = Stream.of("one", "two", "three");
        Set<String> stringSet = stream.collect(Collectors.toSet());
        assertTrue(stringSet.contains("one"));
        assertTrue(stringSet.contains("two"));
        assertTrue(stringSet.contains("three"));

        stream = Stream.of("one", "two", "three");
        String joined = stream.collect(Collectors.joining(", "));
        stream.close();
        System.out.println(joined);
    }

    @Test
    public void fileStreams() throws IOException {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(new File("src/main/java").toPath());
        directoryStream.forEach(System.out::println);
        directoryStream.close();

        Stream<String> linesStream = Files.lines(new File("src/main/java/de.stevenschwenke.java.java8workshop.DeepThought.java").toPath());
        linesStream.forEach(System.out::println);
        linesStream.close();

        // Line by line reading is done without reading the whole file into memory, see http://java.dzone.com/articles/understanding-java-8-streams-1 :
        // "Collections are in-memory data structures which hold elements within it. Each element in the collection is computed before it actually becomes a part of that collection. On the other hand Streams are fixed data structures which computes the elements on-demand basis.
        // The Java 8 Streams can be seen as lazily constructed Collections, where the values are computed when user demands for it. Actual Collections behave absolutely opposite to it and they are set of eagerly computed values (no matter if the user demands for a particular value or not)."
        // and http://winterbe.com/posts/2015/03/25/java8-examples-string-number-math-files/ :
        // "As an memory-efficient alternative you could use the method Files.lines. Instead of reading all lines into memory at once, this method reads and streams each line one by one via functional streams."
    }

    @Test
    public void parallelStreamsRunMultiThreaded() {
        List<String> stringList = Arrays.asList("first", "second", "third", "fourth");

        Stream<String> parallelStream = stringList.parallelStream();
        parallelStream.forEach(System.out::println);
        parallelStream.close();

        // Advanced configuration of parallel streams via custom thread pool,
        // see http://stackoverflow.com/questions/21163108/custom-thread-pool-in-java-8-parallel-stream
    }

    @Test
    public void multiThreadPerformance() {

        // This test is a playground for testing performance between calculating sequential and parallel sum of a
        // long double stream. Play with the length of the stream:
        int lengthOfStream = 2000;

        List<Double> randomDoubleList = new ArrayList<>();
        for (int i = 0; i < lengthOfStream; i++) {
            randomDoubleList.add(Math.random());
        }

        // 1. calculating the sum with a sequential stream
        long start = System.currentTimeMillis();
        Stream<Double> sequentialStream = randomDoubleList.stream();
        Double sumSequential = sequentialStream.reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        long end = System.currentTimeMillis();
        long durationSequential = end - start;
        System.out.println("Sequential calculated sum = " + sumSequential);
        System.out.println("Calculated in " + durationSequential + " ms");
        sequentialStream.close();

        // 2. calculating the sum with a parallel stream
        start = System.currentTimeMillis();
        Stream<Double> parallelStream = randomDoubleList.parallelStream();
        Double sumParallel = parallelStream.reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        end = System.currentTimeMillis();
        long durationParallel = end - start;
        System.out.println("Parallel calculated sum = " + sumParallel);
        System.out.println("Calculated in " + durationParallel + " ms");
        parallelStream.close();

        // Hint: rounding error because of addition

        // QUESTION: Why can we use the list of random doubles here again - shouldn't it be manipulated by the
        // operations above?


        // ANSWER from question above: Input parameters of streams are not changed by stream operations. The list
        // is just the same so we can use it again.

        // CONCLUSION:
        // Runtime with different length of stream very different, dependent on the machine. Sometimes even the
        // sequential stream is faster.
    }

    @Test
    public void splittableRandom() {
        // New class for creating random numbers, that additionally supports streams. To support parallel streams,
        // numbers that are generated in parallel threads should be independent from each other. In other words:
        // this generator is not "shared" between threads, it's "splitted". Also, it's faster then Math.random(). :)

        DoubleStream randomStreamWithThreeDoubles = new SplittableRandom().doubles(3);
        DoubleStream threeRandomNumbersBetween0And100 = new SplittableRandom().doubles(3, 0, 100);
        // actually, the above is [0, 100) = including 0 and < 100
    }

    @Test
    public void alteringDataSourceOfAStream() {
        Queue<String> q = new LinkedList<>();
        q.add("1");
        q.add("2");
        q.stream().peek(x -> {
            System.out.println(x);
            q.add(("NEW")); // ConcurrentModificationException
        }).count();
    }

    @Test
    public void nonInterference() {
        // Although the elements of the underlying collection should not be changed by a stream, it sure is possible,
        // see the code below. However, see http://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#NonInterference,
        // " For most data sources, preventing interference means ensuring that the data source is not modified at all
        // during the execution of the stream pipeline"

        SimpleClass simpleClass1 = new SimpleClass(1);
        System.out.println(simpleClass1.getX()); // "1"

        Stream<SimpleClass> stream = Stream.of(simpleClass1);
        stream.peek(x -> {
            x.setX(42);
        }).forEach(System.out::println); // "42"

        System.out.println(simpleClass1.getX()); // "42" -> the underlying object has been altered by the stream!
    }

    public class SimpleClass {
        private int x;

        public SimpleClass(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        @Override
        public String toString() {
            return ""+x;
        }
    }


    /*

        ... and many more streams at java doc for java.util.stream
        http://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html

        Important side note: Parallel streams are supposed to be faster. However, there are pitfalls. Also, intelligent
        people suggested to never use it because of thread issues. Read into that at
        http://java.dzone.com/articles/think-twice-using-java-8
        http://zeroturnaround.com/rebellabs/java-parallel-streams-are-bad-for-your-health/
     */
}
