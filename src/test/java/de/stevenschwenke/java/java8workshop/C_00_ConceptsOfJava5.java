package de.stevenschwenke.java.java8workshop;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Let's start with some of the most basic concepts that got introduced with Java 1.5: Generics, for-each-loop and
 * varargs.
 */
public class C_00_ConceptsOfJava5 {

    @Test
    public void generics() {

        // Java 1.0
        List list = new ArrayList(Arrays.asList(1, 2, 3));
        Object o = list.get(0);
        Integer i = (Integer) list.get(0);
        String s = (String) list.get(0); // exception at runtime!

        // Java 1.5
        List<Integer> list5 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        o = list5.get(0);
        i = list5.get(0);  // no cast necessary!
//         s = (String) list5.get(0); // exception at compile-time!

        // Java 1.7 (Diamond operator)
        List<Integer> list7 = new ArrayList<>(Arrays.asList(1, 2, 3));
    }

    class MyGenericClass <T,X, N extends Number> {
        private T myT;
        private X myX;
        private N number;
    }

    @Test
    public void usingOwnGenerics() {
        new MyGenericClass<String, String, Integer>();
    }

    @Test
    public void loopsInJava1_2And1_5(){

        // Java 1.2
        List list = new ArrayList(Arrays.asList(1, 2, 3));


        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // Java 5
        List<Integer> list5 = new ArrayList<>(Arrays.asList(1, 2, 3));

        for (Integer i : list5) {
            System.out.println(i);
        }
    }

    @Test
    public void varArgs() {
        varArgsMethod(1,2,3,4,5,6,7);
    }

    public void varArgsMethod(int x, int y, int... z) {
        for(Integer i : z) {
            System.out.println(i);
        }
    }
}
