package de.stevenschwenke.java.java8workshop;

import de.stevenschwenke.java.java8workshop.DeepThought;
import de.stevenschwenke.java.java8workshop.FunctionalInterfaceGen1;
import de.stevenschwenke.java.java8workshop.SimpleFunctionalInterface;
import de.stevenschwenke.java.java8workshop.SlightlyMoreSophisticatedFunctionalInterface;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * From JavaDoc:
 * <p>
 * "Functional interfaces provide target types for lambda expressions and method references."
 * <p>
 * Yeah, now we know what's going on! Well, not quite yet. Let's work through this baby step by baby step.
 */
public class C_01_FunctionalInterfacesAndLambdas {

    /**
     * Functional interfaces are normal interfaces but must only have abstract one method.
     */
    @Test
    public void normalInterfaceOnlyOneAbstractMethod() {

        SimpleFunctionalInterface i = new DeepThought();
        assertEquals(42, i.returnAnswerToUltimateQuestion());

        // -> Functional Interface is annotated as such, but is implemented by a normal class. Boring!
    }

    /**
     * Functional interfaces can be implemented with Lambda expressions.
     */
    @Test
    public void implementingWithLambdas() {

        // All right, functional interfaces must have only one abstract method. This one abstract method can be
        // implemented with lambdas. Yes, that is kind of cool as you will see!

        // Lambdas = closures = function literals = lambda expressions
        // Lambdas are a description of functionality that is executed later ("deferred execution") by another
        // part of the code. That code decides if and under which circumstances the lambda is called. That
        // way, the functionality can be called multiple times.

        SlightlyMoreSophisticatedFunctionalInterface impl = null;

        // Let's implement the method in various ways:

        impl = (int summand1, int summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (final int summand1, final int summand2) -> (summand1 + summand2);
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
        // impl = (final summand1, final summand2) -> (summand1 + summand2);

        // NOPE: mixed inferred and typed argument
        // impl = (int summand1, summand2) -> (summand1 + summand2);


        /*
            Methods and lambdas represent a functionality. Methods however may have side effects, Lambdas don't!
            Lambda take input and do something and give a result back, without any side effects. See next unit test:
         */
    }

    /**
     * Lambda expressions must be effectively final.
     */
    @Test
    public void effectivelyFinal() {

        int x = 3;

        SlightlyMoreSophisticatedFunctionalInterface impl = (a, b) -> {

            //  x = 5; // NOPE! But can be used within this method (just readable).

            return a + b;
        };

        assertEquals(3, impl.sumItUp(1, 2));
    }

    /**
     * Functional interfaces cannot infer functional interfaces.
     */
    @Test
    public void noMultipleInheritance() {
        // See following interface:
        FunctionalInterfaceGen1 x;
    }

    // The execution of Lambdas does not generate anonymous classes. Lambdas are called with invokedynamic right at
    // bytecode-level.

    /**
     * Let's have a look at how Java changed in the past. Our example will be the simple iteration of a list.
     */
    @Test
    public void javaTimeTravel() {

        // Java 1.2
        List list = new ArrayList(Arrays.asList(1, 2, 3));

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // Java 5
        List<Integer> list5 = new ArrayList<>(Arrays.asList(1, 2, 3));

        for (Integer i : list5) {
            System.out.println(i);
        }

        // Java 8 internal iteration
        list5.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });

        // Java 8 Lambdas
        list5.forEach(i -> System.out.println(i));
        // or
        list5.forEach(System.out::println);
    }

    /**
     * To address lambdas, {@link Function} was introduced in Java 8.
     */
    @Test
    public void function() {

        Function<Integer, Integer> add37 = (x) -> x + 37;
        int result = add37.apply(5); // 42

        // Function Chaining:

        Function<Integer, Integer> add37Duplicate = add37.andThen((x) -> x * 2);
        int chainResult = add37Duplicate.apply(5);
    }

    /**
     * A nice application for functional interfaces is the use of the new {@link java.util.function.Predicate}. A
     * predicate is a boolean-valued function.
     */
    @Test
    public void predicate() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println("Print all numbers:");
        evaluate(list, (n) -> true);

        System.out.println("\nPrint no numbers:");
        evaluate(list, (n) -> false);

        System.out.println("Print even numbers:");
        evaluate(list, (n) -> n % 2 == 0);

        System.out.println("\nPrint numbers greater than 8:");
        evaluate(list, (n) -> n > 8);
    }

    private void evaluate(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer i : list) {
            if (predicate.test(i)) {
                System.out.print(i + " ");
            }
        }
    }

    /*
        Having understood this, a little party knowledge on the side:
        - "lambda" comes from the Lambda Calculus which simply is a formal system for computation.
        - introduced by Alonzo Church and Stephen Cole Kleene in the 1930s.
     */
}
