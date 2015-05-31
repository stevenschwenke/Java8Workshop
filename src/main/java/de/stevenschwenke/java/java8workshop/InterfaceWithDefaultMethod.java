package de.stevenschwenke.java.java8workshop;

public interface InterfaceWithDefaultMethod {

    // Watch the new "default" key word! It's the new dude around the block, be nice! The dude brings a cool feature:
    // We can now have an implementation within an interface. Insane these youngsters these days ...
    default public int addStuff(int base) {
        return 42 + base;
    }

}
