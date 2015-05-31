package de.stevenschwenke.java.java8workshop;

/**
 * Functional interface with a default method.
 */
public interface DefaultMethodSame2 {

    default void foo() {
        System.out.println("2");
    }
}
