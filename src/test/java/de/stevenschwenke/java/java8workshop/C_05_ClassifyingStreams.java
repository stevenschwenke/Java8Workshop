package de.stevenschwenke.java.java8workshop;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * In Java Doc special wording for classifying streams-operations. For example operation limit(...) "This is a
 * short-circuiting stateful intermediate operation". This class explains these.
 */
public class C_05_ClassifyingStreams {

    /*
     intermediate vs terminal operations:
     - intermediate operation returns stream, for example filter(...), map(...), peek(...)
     - terminal operations return something else and are the end of a list of operations, for example count(), reduce(...)
    */
    @Test
    public void intermediateAndTerminalOperation() {

        System.out.println("No terminal operation:");
        // No terminal operation, so the intermediate operation is not executed.
        Stream.of(1, 2, 3).peek(System.out::println);

        System.out.println("With terminal operation:");
        // Ah yes - now we see intermediate operation stuff happening - thanks to the terminal operation! :)
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        integerStream.peek(System.out::println).count();
        integerStream.close();

        // Little hint: You now know the intermediate operation peek(). It let's you output your stream without
        // ending it, not like forEach(System.out::println). Very handy for having a peek within your stream.
    }

    @Test
    public void streamsCanNotBeOperatedUponAfterTerminalOperation() {
        Stream<Integer> stream = Stream.of(1, 2, 3);

        // operation possible because stream has not been operated upon:
        stream.peek(System.out::println).reduce(Integer::sum).get();

        // operation NOT possible because stream has been operated upon:
        stream.reduce(Integer::sum).get();
    }

    /*
        Workflow: Each intermediate operation generates a new stream that "knows" which operations to execute on which
        elements of the input-stream. However, these operations are executed when the terminal operation occurs, not
        before! Then, the elements of the backing data source are visited one after the other and each intermediate and the
        terminal operation is performed on each element. The operations are executed vertically, not horizontally.
        Let's say we have three intermediate operations and a stream of 5 elements. Then each of the 5 elements is visited
        after each other and with each visit, every one of the three operations is performed at the current element. This
        way, every element has to be visited only once. This workflow is also necessary for parallel streams.
    */
    @Test
    public void showingWorkflow() {
        String[] txt = {"This", "is", "my", "little", "example", "text"};
        int sum = Arrays.stream(txt).
                filter(s -> s.length() > 1).
                peek(System.out::println).
                map(String::length).
                peek(System.out::println).
                reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
    }

    /*
    Short-circuiting operations

    = operation that doesn't visit all elements of a stream. Can be an intermediate of a final operation.
     */
    @Test
    public void shortCircuitingOperation() {
        // Intermediate short-circuit operation that doesn't visit the last element:
        Stream.of(1, 2, 3).limit(2).forEach(System.out::println);

        // Terminal short-circuit operation that skips every element after the first "1":
        boolean integerOnePresent = Stream.of(1, 2, 3).anyMatch(integer -> integer == 1);
        assertTrue(integerOnePresent);
    }

    /*
    Stateless vs stateful operations

    Stateless operations just need the element to operate and don't need to know anything about the rest of the stream.
    Examples are filter, map.

    Stateful operations need information about the rest of the stream. limit(...) for example needs to count the visited
    elements in order to know when to cancel and return. distinct(...) removes duplicates and hence has to remember the
    visited elements.

    Stateless operations are fast and don't cause trouble when being called in parallel streams. Stateful operations
    however have to be treated differently and are much slower. The most expensive operation is sort(...) because
    it has to copy the whole stream in order to compare its elements.
     */

}
