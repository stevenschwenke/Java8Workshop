import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FeatureShowcase {

    @Test
    public void functionalInterfaceMustHaveOnlyOneAbstractMethod() {
        SimpleFunctionalInterface i = new DeepThought();
        assertEquals(42, i.returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything());
    }

    @Test
    public void implementingFunctionalInterfaceWithLambdaExpression() {

        // Implementing the function interface with different versions of lambda:
        SlightlyMoreSophisticatedFunctionalInterface impl = null;


        impl = (final int summand1, final int summand2) -> (42 + summand1 + summand2);

        impl = (int summand1, int summand2) -> (42 + summand1 + summand2);

        impl = (summand1, summand2) -> (42 + summand1 + summand2);

        impl = (summand1, summand2) -> {
            System.out.println("Blubber");
            return 42 + summand1 + summand2;
        };

        // NOPE: final without type
        //impl = (final summand1, final summand2) -> (42 + summand1 + summand2);

        // NOPE: mixed inferred and typed argument
        //impl = (int summand1, summand2) -> (42 + summand1 + summand2);

        assertEquals(45, impl.addAnswerToUltimateQuestionOfLifeTheUniverseAndEverything(1, 2));
    }

    @Test
    public void lambdaExpressionMustBeEffectivelyFinal() {

        // Methode und Lambda setzen eine Funktion um.
        // Aber: Methoden haben Seiteneffeke - Lambdas nicht!
        // Lambda nimmt Eingangsparameter und gibt einen Rückgabewert ohne Seiteneffekte.

        int x = 3;

        SlightlyMoreSophisticatedFunctionalInterface impl = (a, b) -> {

            //x = 5; // NOPE!

            return a + b;
        };
    }

    @Test
    public void interfaceWithDefaultMethod() {
        InterfaceWithDefaultMethod i = new DefaultMethodImplementingEmptyClass();

        // Default:
        assertEquals(45, i.addAnswerToUltimateQuestionOfLifeTheUniverseAndEverything(3));

        // Overwrite:
        //assertEquals(15, i.addAnswerToUltimateQuestionOfLifeTheUniverseAndEverything(3));
    }
}
