
@FunctionalInterface
public interface SimpleFunctionalInterface {

    public int returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything();

    // Only one abstract method per function interface allowed!
    //public String returnQuestionTo42();

    // But if there's a default implementation, it's alright:
    default public String returnQuestionTo42() {
        return "UH-OH";
    }

    ;
}
