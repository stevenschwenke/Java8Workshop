
@FunctionalInterface
public interface HighlySophisticatedFunctionalInterface {

    // This is the one and only abstract method allowed in this functional interface.
    public int returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything();

    // Only one abstract method per function interface allowed! So this is commented out:
    //public String returnQuestionTo42();

    // But if there's a default implementation, it's alright:
    default public String returnQuestionTo42() {
        return "UH-OH";
    }
}
