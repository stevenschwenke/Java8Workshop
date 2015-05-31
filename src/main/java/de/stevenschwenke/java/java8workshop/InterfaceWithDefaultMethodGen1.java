package de.stevenschwenke.java.java8workshop;

public interface InterfaceWithDefaultMethodGen1 {

    // Although this likable and polite method doesn't harm anybody, it gets overridden by the younger generation and
    // is never called. Sad.
    default String getSomeString() {
        return "Hello there. I am the honorable generation 1.";
    }
}
