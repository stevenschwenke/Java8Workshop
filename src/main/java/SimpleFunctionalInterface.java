
// This is just a marker annotation! Can be removed but nice to have here for beginners. ;)
@FunctionalInterface
public interface SimpleFunctionalInterface {

    // This is the one and only abstract method allowed in this functional interface.
    public int returnAnswerToUltimateQuestion();

    // That one is not allowed:
    // public int returnAnotherAnswerToUltimateQuestionOfLifeTheUniverseAndEverything();
}
