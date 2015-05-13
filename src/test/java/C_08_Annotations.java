import org.junit.Test;

import java.util.List;

@JavaVersion("1.5")
public class C_08_Annotations {

    /*
        See http://docs.oracle.com/javase/tutorial/java/annotations/basics.html:
        Annotations can be applied to declarations: declarations of classes, fields, methods, and other program
        elements. When used on a declaration, each annotation often appears, by convention, on its own line.

        As of the Java SE 8 release, annotations can also be applied to the use of types. Here are some examples:
        1. Class instance creation expression:
            new @Interned MyObject();

        2. Type cast:
            myString = (@NonNull String) str;

        3. implements clause:
            class UnmodifiableList<T> implements
                @Readonly List<@Readonly T> { ... }

        4. Thrown exception declaration:
            void monitorTemperature() throws
                @Critical TemperatureException { ... }
     */

    private @JavaVersion("1.5") String attributeString;

    @JavaVersion("1.5")
    private String otherAttributeString;

    @JavaVersion("1.5")
    private void doNothing() {

    }

    @Test
    public void annotationExamples() throws @JavaVersion("1.8") Exception {

        @JavaVersion("1.5")
        String someString0 = null;

        String someString1 = new @JavaVersion("1.8") String("someString");

        String someString2 = (@JavaVersion("1.8") String) someString1;

        List<@JavaVersion("1.8") String> strings;

        /*
            That looks cool, but why?! Imagine annotations that tell something about the USE of a type, not just the
            DECLARATION of a type. Imagine a method annotated with @NonNullArguments can get parsed if it really
            doesn't get null.
         */
    }

    @Change(date = "2015-03-15", reason="First version of this method")
    @Change(date = "2015-03-18", reason="small bug fix")
    public void repeatableAnnotations() {

    }

    // Repetition is a source of learning:
    // Remember: Callable is designed to be used with executor, not stand-alone.
    // Since Java 8, fluent API possible even with concurrent code.
}
