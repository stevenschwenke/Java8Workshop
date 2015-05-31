package de.stevenschwenke.java.java8workshop;

@FunctionalInterface
public interface FunctionalInterfaceGen1 {
    // This is not allowed because the resulting interface would have two methods and hence cannot be a functional
    // interface.
    //public interface de.stevenschwenke.java.java8workshop.FunctionalInterfaceGen1 extends de.stevenschwenke.java.java8workshop.FunctionalInterfaceGen2 {
    public void doStuff();
}
