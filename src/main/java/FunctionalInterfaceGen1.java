@FunctionalInterface
public interface FunctionalInterfaceGen1 {
    // This is not allowed because the resulting interface would have two methods and hence cannot be a functional
    // interface.
    //public interface FunctionalInterfaceGen1 extends FunctionalInterfaceGen2 {
    public void doStuff();
}
