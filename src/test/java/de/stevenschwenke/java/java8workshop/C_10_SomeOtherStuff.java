package de.stevenschwenke.java.java8workshop;

import de.stevenschwenke.java.java8workshop.InterfaceWithStaticMethod;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * This class is for small features that didn't fit in the other test classes.
 */
public class C_10_SomeOtherStuff {

    /**
     * Interfaces are allowed to have static methods now.
     */
    @Test
    public void interfacesStaticMethods() {
        assertEquals(1, InterfaceWithStaticMethod.staticMethodWithinAnInterface());
    }

    /*
        Permanent generation is gone. All data that lived in this fixed-sized memory will be in the new Metaspace
        that can expand at runtime. This however bloats the JVM process until the machine dies on low memory. Can be
        avoided with -XX:MaxMetaspaceSize.
        More at http://stackoverflow.com/questions/20563047/jdk-8-memory-layout-and-garbage-collection
     */

    /**
     * Ability of the compiler to recognize the type from context improved.
     */
    @Test
    public void improvedTypeDeduction() {

        // Worked since there are generics:
        List<String> list = new ArrayList<>();

        // Works since Java 8:
        list = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * <p>
     * Since forever: JavaScript-engine Rhino. Gets replaced with Nashorn in Java 8.
     * <p>
     * <ul>
     * <li> Nashorn 2 to 10 times faster than Rhino.</li>
     * <li>Enables "free standing JavaScript applications using the jrunscript command-line tool"
     * (see openjdk.java.net/projects/nashorn).</li>
     * <li> Nashorn: 100% ECMA-Script conform, Rhino just 95.9%</li>
     * </ul>
     * </p>
     * Reasons for Java supporting JavaScript:
     * <ul>
     * <li>enabling user to script dynamically</li>
     * <li>establishing JVM as platform for more programming languages</li>
     * <li>script languages have less boiler plate code and are often easier to read</li>
     * <li>frontend developer (if there is such a thing these days) can code backend without learning a new language</li>
     * </ul>
     */
    @Test
    public void javaScript() throws ScriptException {
        // CLI: run bin/jjs:

        // jjs> print("This is JavaScript!");
        // jjs> quit();

        // CLI with js file:
        // bin/jjs d:\myJavaScript.js

        // in Java:
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine nashorn = factory.getEngineByName("nashorn");
        nashorn.eval("print(\"This is JavaScript in Java\");");

        // There are more features, like compiling code to bytecode. Not looked into here. :)
    }

    @Test
    public void optional() {

     /*
        java.util.Optional

        - can contain a value or be empty - in other words: has an optional value
        - answers the question if there is a result or not (and, if present, encapsulates the result)
        - return-type of some stream operations
     */

        // This is easy:
        Stream<Integer> integerStream = Stream.of(-2, -1, 0, 1, 2);
        int sum = integerStream.reduce(0, (a, b) -> a + b);
        assertEquals(0, sum);

        // The sum of this empty stream is 0, as above. However, this 0 results from the identity
        // given to the reduce() - method. So in this case, the result is of complete different nature.
        Stream<Integer> emptyStream = Stream.of();
        sum = emptyStream.reduce(0, (a, b) -> a + b);
        assertEquals(0, sum);

        // Here comes the Optional:
        emptyStream = Stream.of(-2,-1,0,1,2);
        Optional<Integer> optional = emptyStream.reduce((a, b) -> a + b);
        assertEquals(0, optional.get().intValue()); // OK

        emptyStream = Stream.of();
        optional = emptyStream.reduce((a, b) -> a + b);
//        assertEquals(0, optional.get().intValue()); // "NoSuchElementException: No value present"

        // Better:
        if(optional.isPresent()) {
            assertEquals(0, optional.get().intValue());
        } else {
            fail("Hey, your stream was empty!");
        }

        // -> Optional as a NullObject (http://en.wikipedia.org/wiki/Null_Object_pattern)

        // Creating Optional objects:
        Optional.empty();
        Optional.of("String");
        Optional.ofNullable(null);

        // And now all together with some eye candy:
        Stream.of(-2,-1,0,1,2).reduce((a,b)->a+b).ifPresent(s -> System.out.println(s));
    }


    // Repetition is a source of learning:
    // There's a new class in Java 8 which allows filtering in lists. What's the name of this class?
    // There's a new method in the Task-class that gives information about the progress of a task. What's its name?
    // What does the new ScheduledService class do?
}
