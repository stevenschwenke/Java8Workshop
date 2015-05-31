package de.stevenschwenke.java.java8workshop;

public class DefaultMethodOverridingClass implements InterfaceWithDefaultMethod {

    // This is an overwrite for the default method in the interface:
    public int addStuff(int base) {
        return 12 + base;
    }
}
