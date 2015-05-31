package de.stevenschwenke.java.java8workshop;

@FunctionalInterface
public interface HighlySophisticatedFunctionalInterface {

    // This is the one and only abstract method allowed in this functional interface.
    public int returnAnswerToUltimateQuestionOfLifeTheUniverseAndEverything();

    // But if there's a default implementation, it's alright:
    default public String returnQuestionTo42() {
        return "UH-OH";
    }
}
