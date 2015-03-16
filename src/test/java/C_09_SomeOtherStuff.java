import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class is for small features that didn't fit in the other test classes.
 */
public class C_09_SomeOtherStuff {

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
}
