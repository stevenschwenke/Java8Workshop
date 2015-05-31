package de.stevenschwenke.java.java8workshop;

/**
 * Functional interface with a default method.
 */
public interface DefaultMethodSame1 {

    default void foo() {
        System.out.println("1");
    }
}
