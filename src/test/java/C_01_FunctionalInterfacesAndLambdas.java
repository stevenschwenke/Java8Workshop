import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * From JavaDoc:
 * <p>
 * "Functional interfaces provide target types for lambda expressions and method references."
 * <p>
 * Yeah, now we know what's going on! Well, not quite yet. Let's work through this baby step by baby step.
 */
public class C_01_FunctionalInterfacesAndLambdas {

    /**
     * TODO Bei jeder Testklasse den zu langen Testnamen als Doc hinzufügen.
     */
    @Test
    public void functionalInterfaceIsANormalInterfaceButMustHaveOnlyOneAbstractMethod() {

        SimpleFunctionalInterface i = new DeepThought();
        // TODO zu lange Methode
        assertEquals(42, i.returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything());

        // -> Functional Interface is annotated as such, but is implemented by a normal class. Boring!
    }

    @Test
    public void implementingFunctionalInterfaceWithLambdaExpression() {

        // All right, functional interfaces must have only one abstract method. This one abstract method can be
        // implemented with lambdas. Yes, that is kind of cool as you will see!

        // Lambdas = closures = function literals = lambda expressions

        SlightlyMoreSophisticatedFunctionalInterface impl = null;

        // Let's implement the method in various ways:

        impl = (int summand1, int summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (final int summand1, final int summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));


        impl = (summand1, summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (summand1, summand2) -> {
            // some much too complicated code here
            System.out.println("Logging stuff!");
            return summand1 + summand2;
        };
        assertEquals(3, impl.sumItUp(1, 2));

        // NOPE: final without type
        // impl = (final summand1, final summand2) -> (summand1 + summand2);

        // NOPE: mixed inferred and typed argument
        // impl = (int summand1, summand2) -> (summand1 + summand2);


        /*
            Methods and lambdas represent a functionality. Methods however may have side effects, Lambdas don't!
            Lambda take input and do something and give a result back, without any side effects. See next unit test:
         */
    }

    @Test
    public void lambdaExpressionMustBeEffectivelyFinal() {

        int x = 3;

        SlightlyMoreSophisticatedFunctionalInterface impl = (a, b) -> {

            //  x = 5; // NOPE! But can be used within this method (just readable).

            return a + b;
        };

        assertEquals(3, impl.sumItUp(1, 2));
        // TODO Carsten fragen: Bei Entw-Treffen statische Klassenvariable trotzdem verändern?
    }


    @Test
    public void functionalInterfaceCannotInferFunctionalInterface() {
        // See following interface:
        FunctionalInterfaceGen1 x;
    }

    // The execution of Lambdas does not generate anonymous classes. Lambdas are called with invokedynamic right at
    // bytecode-level.

    // TODO Übung: Swing-Button mit ActionListener refactorn. Ein Button mit einzeiligem Body, einen mit mehrzeiligem Body.
}
