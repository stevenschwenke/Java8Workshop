import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

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
        System.out.println();
        list.forEach(s -> System.out.print(s));
    }

    @Test
    public void methodReferenceToVariable() {
        // Consumer is a new functional interface that takes one argument and returns void.
        Consumer<String> consumerReference = System.out::print;
        consumerReference.accept("Printed string by reference to variable!");
    }


    // TODO java8.org -> Cheatsheet
    // TODO main focus on Java 8. But also touch versions 1.5 to 1.8.
    // TODO review all new packages for new classes and add them here

    // TODO Create exercises for the participants
}
