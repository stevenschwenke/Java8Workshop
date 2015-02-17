import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * Method references allow to identify methods without actually calling them.
 */
public class C_03_MethodReferences {

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
    }

    @Test
    public void methodReferenceToVariable() {
        // Consumer is a new functional interface that takes one argument and returns void.
        Consumer<String> consumerReference = System.out::println;
        consumerReference.accept("Printed string by reference to variable!");

        // Supplier is also a new functional interface:
        Supplier<Double> supplierReference = Math::random;
        consumerReference.accept(supplierReference.toString());

        // Wait, we wanted to print that random number! With .get() we get the value of a supplier:
        consumerReference.accept(supplierReference.get().toString());
    }

    @Test
    public void bla() {
        // TODO Aus Torstens Java 8 Folie

        Function<Integer, Integer> add37 = (x) -> x + 37;
        int result = add37.apply(5); // 42

        // Function Chaining:

        Function<Integer, Integer> add37Duplicate = add37.andThen((x) -> x * 2);
        int chainResult = add37Duplicate.apply(5);


    }

    @Test
    public void constructorMethodReferences() {
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
        BiFunction<String, String, Integer> concatInts = concat.andThen(Integer::new);
        Integer result = concatInts.apply("1", "2");
        assertEquals(new Integer(12), result);
    }
}
