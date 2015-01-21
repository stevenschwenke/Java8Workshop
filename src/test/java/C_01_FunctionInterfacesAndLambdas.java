import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO write some prose here
 */
public class C_01_FunctionInterfacesAndLambdas {


    @Test
    public void functionalInterfaceMustHaveOnlyOneAbstractMethod() {

        // Functional Interface is annotated as such, but is implemented by a normal class. Boring!

        SimpleFunctionalInterface i = new DeepThought();
        assertEquals(42, i.returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything());
    }

    @Test
    public void implementingFunctionalInterfaceWithLambdaExpression() {

        // Functional Interface can also be implementing with Lambdas. Here are some different versions:
        SlightlyMoreSophisticatedFunctionalInterface impl = null;


        impl = (final int summand1, final int summand2) -> (summand1 + summand2);
        assertEquals(3, impl.sumItUp(1, 2));

        impl = (int summand1, int summand2) -> (summand1 + summand2);
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
        //impl = (final summand1, final summand2) -> (summand1 + summand2);

        // NOPE: mixed inferred and typed argument
        //impl = (int summand1, summand2) -> (summand1 + summand2);

        // Methods and lambdas represent a functionality. Methods however may have side effects, Lambdas don't!
        // Lambda take input and do something and give a result back, without any side effects.
    }

    @Test
    public void lambdaExpressionMustBeEffectivelyFinal() {

        int x = 3;

        SlightlyMoreSophisticatedFunctionalInterface impl = (a, b) -> {

            // x = 5; // NOPE! But can be used within this method (just readable).

            return a + b;
        };

        assertEquals(3, impl.sumItUp(1, 2));
    }


    @Test
    public void functionalInterfaceCannotInferFunctionalInterface() {
        // See following interface:
        FunctionalInterfaceGen1 x;
    }

}
