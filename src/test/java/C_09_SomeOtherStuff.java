import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class is for small features that didn't fit in the other test classes.
 */
public class C_09_SomeOtherStuff {

    @Test
    public void interfacesCanHaveStaticMethods() {
        assertEquals(1, InterfaceWithStaticMethod.staticMethodWithinAnInterface());
    }
}
