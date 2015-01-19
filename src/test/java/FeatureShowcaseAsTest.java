import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FeatureShowcaseAsTest {

    // 1. Functional Interfaces and Lambdas

    @Test
    public void functionalInterfaceMustHaveOnlyOneAbstractMethod() {

        // Functional Interface is annotated as such, but is implemented by a normal class. Boring!

        SimpleFunctionalInterface i = new DeepThought();
        assertEquals(42, i.returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything());
    }

    @Test
    public void implementingFunctionalInterfaceWithLambdaExpression() {

        // Functional Interface can also be implementing with Lambdas. Here are some different versions:
        SlightlyMoreSophisticatedFunctionalInterface impl = null;


        impl = (final int summand1, final int summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (int summand1, int summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (summand1, summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (summand1, summand2) -> {
            // some much too complicated code here
            System.out.println("Logging stuff!");
            return summand1 + summand2;
        };
        assertEquals(3, impl.sumItUp(1, 2));

        // NOPE: final without type
        //impl = (final summand1, final summand2) -> (summand1 + summand2);

        // NOPE: mixed inferred and typed argument
        //impl = (int summand1, summand2) -> (summand1 + summand2);

        // Methods and lambdas represent a functionality. Methods however may have side effects, Lambdas don't!
        // Lambda take input and do something and give a result back, without any side effects.
    }

    @Test
    public void lambdaExpressionMustBeEffectivelyFinal() {

        int x = 3;

        SlightlyMoreSophisticatedFunctionalInterface impl = (a, b) -> {

            // x = 5; // NOPE! But can be used within this method (just readable).

            return a + b;
        };

        assertEquals(3, impl.sumItUp(1, 2));
    }


    @Test
    public void functionalInterfaceCannotInferFunctionalInterface() {
        // See following interface:
        FunctionalInterfaceGen1 x;
    }

    // 2. Default Methods

    @Test
    public void interfaceWithDefaultMethod() {
        InterfaceWithDefaultMethod i = new DefaultMethodImplementingEmptyClass();

        // Default:
        assertEquals(45, i.addStuff(3));

        // Overwrite:
        InterfaceWithDefaultMethod inter2 = new DefaultMethodOverridingClass();
        assertEquals(15, inter2.addStuff(3));
    }

    @Test
    public void functionalInterfaceAndDefaultMethodTogether() {

        HighlySophisticatedFunctionalInterface inter = () -> 42;

        assertEquals(42, inter.returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything());
        assertNotNull(inter.returnQuestionTo42());
    }

    @Test
    public void defaultMethodsWillBeOverriddenByLowerImplementations() {
        InterfaceWithDefaultMethodGen2 gen2 = new InterfaceWithDefaultMethodGen2() {
        };
        System.out.println(gen2.getSomeString());
    }

    // Let's take a break here.
    //
    // Default methods seem to be a really nice add-on to the language. However, they have been introduced mainly
    // to make the new Stream-API possible. The common developer may use them, but there are several blog posts
    // that suggest not to use them as a regular tool. Doing so could result in a mess if your inheritance hierarchy is
    // more complex. Imagine having to "climb up" the inheritance tree to find out what interface overrides which
    // default method. That's just one additional layer of complexity. To avoid problems here, continue implementing
    // interfaces. Use default methods only for what they have been introduced: Enhancing old APIs that have to be
    // downwards compatible.

    // 3. Interfaces can have static methods

    @Test
    public void interfaceHasAStaticMethod() {
        assertEquals(1, InterfaceWithStaticMethod.staticMethodWithinAnInterface());
    }

    // 4. Method references
    // identify methods without calling them.

    @Test
    public void methodReferencesForSortingAList() {

        List<String> list = Arrays.asList("Tiny", "Much much long", "Normal", "Slightly long");

        // Java 7:

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() == o2.length()) return 0;
                return o1.length() > o2.length() ? 1 : -1;
            }
        });
        System.out.println(String.join(", ", list));

        // Java 8:
        list = Arrays.asList("Tiny", "Much much long", "Normal", "Slightly long");

        list.sort(Comparator.comparingInt(String::length));
        System.out.println(String.join(", ", list));

        // Using method references for a print of the list (however without separators)
        list.forEach(System.out::print);
        System.out.println();
        list.forEach(s -> System.out.print(s));
    }

    @Test
    public void methodReferenceToVariable() {
        // Consumer is a new functional interface that takes one argument and returns void.
        Consumer<String> consumerReference = System.out::print;
        consumerReference.accept("Printed string by reference to variable!");
    }

    // 5 Streams

    // Stream is not a data structure, it doesn't hold data. It just holds references to the underlying stream
    // source. Streams are pipelines that handle data structures to operations.
    //
    // Streams = intermediate operations (filter, map) + terminal operation (reduce, sum)

    @Test
    public void waysOfCreatingStreams() {

        // 1. collection-based
        String[] stringArray = {"first", "second", "third", "fourth"};
        List<String> stringList = Arrays.asList(stringArray);

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
        Supplier<Double> random = Math::random;

        // This stream has no intermediate operations and one terminal operation (println):
        Stream.generate(random).forEach(System.out::println);
    }

    @Test
    public void advancedStream() {

        // This stream has two intermediate operations and one terminal operation (println):
        System.out.println("First stream:");
        Stream.generate(Math::random).limit(3).sorted().forEach(System.out::println);

        // That is the same as above:
        System.out.println("\n2nd stream:");
        Stream.generate(Math::random).limit(3).sorted().forEach((x) -> System.out.println(x));
    }

    @Test
    public void streamsMultiThread() {
        List<String> stringList = Arrays.asList("first", "second", "third", "fourth");

        Stream<String> parallelStream = stringList.parallelStream();
        parallelStream.forEach(System.out::println);
    }

    @Test
    public void streamsMultiThreadPerformance() {

        List<Double> randomDoubleList = new ArrayList<>();
        for (int i = 0; i < 2000000; i++) {
            randomDoubleList.add(Math.random());
        }

        long start = System.currentTimeMillis();
        Double sumSequential = randomDoubleList.stream().reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        long end = System.currentTimeMillis();
        long durationSequential = end - start;
        System.out.println("Sequential calculated sum = " + sumSequential);
        System.out.println("Calculated in " + durationSequential + "ms");

        // QUESTION: Why can we use the list of random doubles here again - shouldn't it be manipulated by the
        // operations above?

        start = System.currentTimeMillis();
        Double sumParallel = randomDoubleList.parallelStream().reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();
        end = System.currentTimeMillis();
        long durationParallel = end - start;
        System.out.println("Parallel calculated sum = " + sumParallel);
        System.out.println("Calculated in " + durationParallel + "ms");

        // ANSWER from question above: Input parameters of streams are not changed by stream operations. The list
        // is just the same so we can use it again.

        // TODO values from both calculations are not the same!
        // TODO 9 million operations are nearly equal in time - 3 is very different. Why?
    }

    // TODO java8.org -> Cheatsheet
    // TODO main focus on Java 8. But also touch versions 1.5 to 1.8.
    // TODO review all new packages for new classes and add them here

    // TODO Create exercises for the participants
}
