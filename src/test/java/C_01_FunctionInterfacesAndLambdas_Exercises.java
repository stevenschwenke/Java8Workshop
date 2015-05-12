import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercises for chapter 1: functional interfaces and lambdas.
 */
public class C_01_FunctionInterfacesAndLambdas_Exercises {

    /*
        Task:
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
            driveables.forEach(x -> System.out.println(x));
        }
    }

    @Test
    public void test() {
        GermanAutobahn germanAutobahn = new GermanAutobahn();
        germanAutobahn.addDrivable(() -> "Driving blue car");
        germanAutobahn.addDrivable(() -> "Driving red car");
        germanAutobahn.printTrafficReport();
    }
}
