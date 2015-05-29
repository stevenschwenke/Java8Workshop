import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Exercises for chapter 1: functional interfaces and lambdas.
 */
public class C_01_FunctionalInterfacesAndLambdas_Exercises {

    /*
        Task 1:
        Write the following code using both functional interfaces and lambdas:
        1. Interface "Drivable" for a thing that can drive. Driving things return a String when being asked to drive.
        2. Class "GermanAutobahn" that holds any number of Drivables. There has to be a method printTrafficReport()
           that prints a messages what's currently going on on the autobahn.
        3. Test-method that creates a GermanAutobahn, sets a number of Drivables in it and prints a traffic report to
           console.
     */





    // Intentionally left blank








    public interface Drivable {
        String drive();
    }

    public class GermanAutobahn {

        private List<Drivable> driveables = new ArrayList<>();

        public void addDrivable(Drivable d) {
            driveables.add(d);
        }

        public void printTrafficReport() {
            System.out.println("On the autobahn there are:");
            driveables.forEach(x -> System.out.println(x.drive()));
        }
    }

    @Test
    public void test() {
        GermanAutobahn germanAutobahn = new GermanAutobahn();
        germanAutobahn.addDrivable(() -> "Driving blue car");
        germanAutobahn.addDrivable(() -> "Driving red car");
        germanAutobahn.printTrafficReport();
    }

    /*
        Task 2:
        The autobahn shall not be used by blue cars. Blue cars are bad because ... they are blue! Write a Predicate
        that filters out blue cars from the autobahn!
     */








    // Intentionally left blank



    @Test
    public void filterOutBlueCars() {

        List<Drivable> drivables = Arrays.asList(() -> "blue car", () -> "red car", () -> "orange car");

        Predicate<Drivable> predicate = o -> !o.drive().contains("blue");

        evaluate(drivables, predicate);
    }

    private void evaluate(List<Drivable> list, Predicate<Drivable> predicate) {
        for (Drivable i : list) {
            if (predicate.test(i)) {
                System.out.println(i.drive());
            }
        }
    }
}
