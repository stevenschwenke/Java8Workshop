package de.stevenschwenke.java.java8workshop;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

/**
 * Those examples are from a project I'm working in.
 *
 * @author dmitrij-drandarov
 * @since 14 Feb 17
 */
public class C_13_Stream_Best_Practice {

    private Map<Integer, DummyFruit> dummyFruits;

    @Before
    void dummy() {
        dummyFruits = new HashMap<>();
        dummyFruits.put(1, new DummyFruit("Baby Banana", "It's yellow!", 20.0, DummyFruit.TYPE.BANANA));
        dummyFruits.put(2, new DummyFruit("Granny Smith Apple", "Delicious!", 10.5, DummyFruit.TYPE.APPLE));
        dummyFruits.put(3, new DummyFruit("Grapefruit", "It's totally an orange!", 8.5, DummyFruit.TYPE.ORANGE));
    }

    /**
     * A common mistake that people make when using Optionals, is using the {@link Optional#get}-Method without using
     * {@link Optional#isPresent()} to check for its, well... presence.
     *
     * So instead you should use {@link Optional#orElse} or any of it's equivalents. This way you can define exactly
     * what you want if that {@link Optional} is indeed empty. It can be either a 'null', an empty variance of that Pojo
     * you're searching or whatever else you need for a certain use case. However in any case you will exactly know what
     * you'll be getting.
     * Also --> Readabilty!
     */
    @Test
    void mistakesWithOptionals() {
        //Meh.
        DummyFruit foundFruit1 = dummyFruits.values().stream()
                .filter(dummyFruit -> "Baby Banana".equals(dummyFruit.getName())).findFirst().get();
        assertNotNull(foundFruit1);                                                    //^ unchecked

        //Better
        DummyFruit foundFruit2 = dummyFruits.values().stream()
                .filter(dummyFruit -> "Baby Banana".equals(dummyFruit.getName())).findFirst().orElse(null);
        assertNotNull(foundFruit2);                                                    //^ save and readable
    }



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
