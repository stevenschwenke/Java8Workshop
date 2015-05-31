package de.stevenschwenke.java.java8workshop;

public interface InterfaceWithDefaultMethodGen2 extends InterfaceWithDefaultMethodGen1 {

    // This hectic method overrides the older method in the parent interface.
    default String getSomeString() {
        return "Hi! I'm the fancy dancy fresh gen 2!";
    }
}
