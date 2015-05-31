package de.stevenschwenke.java.java8workshop;

public interface InterfaceWithStaticMethod {

    static public int staticMethodWithinAnInterface() {
        System.out.println("Who would've thought this ...");
        return 1;
    }
}
