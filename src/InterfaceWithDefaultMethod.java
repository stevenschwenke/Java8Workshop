public interface InterfaceWithDefaultMethod {

    //public int addAnswerToUltimateQuestionOfLifeTheUniverseAndEverything(int base);

    default public int addAnswerToUltimateQuestionOfLifeTheUniverseAndEverything(int base) {
      return 42 + base;
    }

}
