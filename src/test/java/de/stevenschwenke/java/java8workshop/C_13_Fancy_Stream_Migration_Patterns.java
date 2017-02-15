package de.stevenschwenke.java.java8workshop;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Those examples are from a project I'm working in. I recently refactored them to Streams and wanted to share those
 * results. These examples are also not automatically convertible to Streams by IDEs right now.
 *
 * Rule of thumb: If you don't know whether you can implement a certain use case with streams, you probably can, however
 * just put a stream in your favorite IDE (preferably IntelliJ IDEA ;p) and use auto-completion. If there is a method
 * that sounds like your use case, it will probably work.
 *
 * @author dmitrij.drandarov
 * @since 14 Feb 17
 */
public class C_13_Fancy_Stream_Migration_Patterns {

    private Map<Integer, DummyFruit> dummyFruits;

    @Before
    public void dummy() {
        dummyFruits = new HashMap<>();
        dummyFruits.put(1, new DummyFruit("Baby Banana", "It's yellow!", 20.0, DummyFruit.TYPE.BANANA));
        dummyFruits.put(2, new DummyFruit("Granny Smith Apple", "Delicious!", 10.5, DummyFruit.TYPE.APPLE));
        dummyFruits.put(3, new DummyFruit("Grapefruit", "It's totally an orange!", 8.5, DummyFruit.TYPE.ORANGE));
    }

    /**
     * A simple old pattern of iteration over your {@link java.util.Collection} and finding the first (and presumably
     * exact) match to your query.
     */
    @Test
    public void findFirstPattern() {
        DummyFruit dummyFruit1 = null;
        for (final DummyFruit DummyFruit : dummyFruits.values()) {
            if (DummyFruit.getName().equals("Grapefruit")) {
                dummyFruit1 = DummyFruit;
                break;
            }
        }
        assertNotNull(dummyFruit1);

        /*
            | | | | | | |
            v v v v v v v
        */

        //Better and sexier code
        assertNotNull(dummyFruits.values().stream()
                .filter(dummyFruit -> "Grapefruit".equals(dummyFruit.getName())).findFirst().orElse(null));
    }

    /**
     * Just as simple as the one above, this is basically a contains check that incorportates some sort of field of the
     * pojo so a simple {@link java.util.List#contains(Object)} won't do it. There are also other kinds of data models
     * that don't include any kind of containment-check.
     */
    @Test
    public void anyMatchPattern() {
        Boolean neueMarkeGefunden = false;
        for(DummyFruit DummyFruit : dummyFruits.values()) {
            if("Baby Banana".equals(DummyFruit.getName()))
                neueMarkeGefunden = true;
        }
        assertTrue(neueMarkeGefunden);

        /*
            | | | | | | |
            v v v v v v v
        */

        //Better and sexier code
        assertTrue(dummyFruits.values().stream().anyMatch(DummyFruit -> "Baby Banana".equals(DummyFruit.getName())));
    }

    //More Patterns to come



    /** Simple Pojo-Class */
    private static class DummyFruit {

        private String name;
        private String description;
        private double value;
        private TYPE type;

        enum TYPE { ORANGE, APPLE, BANANA }

        DummyFruit(String name, String description, double value, TYPE type) {
            this.name = name;
            this.description = description;
            this.value = value;
            this.type = type;
        }

        String getName() {
            return name;
        }
        String getDescription() {
            return name;
        }
        double getValue() {
            return value;
        }
        TYPE getType() {
            return type;
        }
    }

}
