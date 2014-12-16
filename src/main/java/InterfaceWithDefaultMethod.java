public interface InterfaceWithDefaultMethod {

    default public int addStuff(int base) {
        return 42 + base;
    }

}
