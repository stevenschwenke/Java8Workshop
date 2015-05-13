/**
 * Exercises for chapter 4: Streams.
 */
public class C_04_Streams_Exercises {

    /*
        Task 1:
        a) Create a stream with the names of the attendants of this course. Traverse the stream and print out
           the names.
        b) Create a parallel stream and do the same. Is the output different? Why?
     */

    /*
        Task 2:
        Expand your code from task 1 and filter out every name containing the letter "e". How many elements does
        the stream have now?
     */

    /*
        Task 3:
        See task 2 from chapter 1. Use streams to simplify the Test.
     */



    // Intentionally left blank





    /*
        Solution task 1: Remove evaluate-method and use the following code:

        Stream<Drivable> goodCarsStream = drivables.stream().filter(predicate);
        goodCarsStream.forEach(o -> System.out.println(o.drive()));
     */


    // Repetition is a source of learning:
    // What are method references?
    // When are method references called?
    // Can there be a method reference for a constructor?
}
