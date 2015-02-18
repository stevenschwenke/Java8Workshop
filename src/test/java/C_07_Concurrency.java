import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Java 8 brings a lot of support for concurrent programming. These are not main topic of this workshop and are just
 * mentioned here for further reading.
 */
public class C_07_Concurrency {

     /*
        Java 8 brings a lot of support for concurrent programming. These are not topic of this workshop and are just
        mentioned here for further reading:

        - ForkJoinPool, introduced in Java 7, got overhauled.

         // TODO what exactly?
         - redesign class ForkJoinPool: had just one submit queue for external tasks, now has several. Much more
         performance for applications with a lot of users that submit tasks simultaneously.
         - common pool: ForkJoinPool has new method commonPool() that returns a singleton instance of the
         ForkJoinPool. Used for parallel streams.
         - new ForkJoinTask besides existing RecursiveTask and RecursiveAction: CountedCompleter. All three classes
         are used for recursive programming. CountedCompleter builds a tree structure of java objects while
         traversing the recursion
         - todo completableFuture.
         - better accumulators
         - new lock: StampedLock

     */

    /*
    before: Runnable
    Java 7: Callable - can return any object able to throw exception

    ??
    Future - ?

    Java 8
    - CompletionStage + CompletableFuture

    */

    /**
     * Simple Runnable that cannot be parametrized, cannot throw a checked exception and returns nothing. It
     * just runs.
     */
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }

            // Notice: no return value here!
        }
    }

    @Test
    public void runnableTest() {
        Thread t1 = new Thread(new MyRunnable());
        Thread t2 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
    }

    /**
     * Callable is introduced in Java 5. Here is a simple Callable that is parametrized with a Boolean.
     */
    static class MyCallable implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            for (int i = 1; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }

            // Notice: Return value possible!
            return Boolean.TRUE;
        }
    }

    @Test
    public void callableTest() throws Exception {

        // As you see, Callable cannot instantiate a Thread directly. It abstracts the Thread to a higher level and
        // is intended to be used with ExecutorService.

        MyCallable callable1 = new MyCallable();
        MyCallable callable2 = new MyCallable();
        callable1.call();
        callable2.call();
    }


    /**
     * However, Callable is intended to be used in services like the Executor class. For better readability, I used
     * Lambdas to implement the Callable.
     */
    @Test
    public void callableTestWithFuture() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Future<String>> list = new ArrayList<>();

        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return Thread.currentThread().getName();
        };

        // work with 100 instances of the callable
        for (int i = 0; i < 100; i++) {
            Future<String> future = executor.submit(callable);
            list.add(future);
        }

        // Future-objects represent the result of the callable.
        for (Future<String> fut : list) {
            // This line gets executed when the Future ist ready. That causes the output delay in console.
            System.out.println(new Date() + " @ " + fut.get());
        }
        executor.shutdown();
    }


        /*
    FutureTask, introduced in Java 8, implements RunnableFuture interface, which extends Runnable and Future
    interfaces. Hence, FutureTask can be passed to new Thread(futureTask) and ExecutorService.submit(futureTask).
     */


    @Test
    public void bla() throws Exception {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return Thread.currentThread().getName();
        };

        Callable<String> callable2 = () -> {
            Thread.sleep(2000);
            return Thread.currentThread().getName();
        };

        FutureTask<String> futureTask1 = new FutureTask<>(callable);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done");
                    executor.shutdown();
                    return;
                }

                if (!futureTask1.isDone()) {
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }
    }

//    @Test
//    public void CompletableFuture() throws Exception {
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//
//        // Supplier doesn't throw exceptions and can be used for nice fluent programming, see below.
//        Supplier<String> task = () -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return Thread.currentThread().getName();
//        };
//
//        // work with 100 instances of the callable
//        for (int i = 0; i < 100; i++) {
//            CompletableFuture.supplyAsync(task, executor).thenAccept(s -> System.out.println(new Date() + " @ " +
//                    s)).get();
//            CompletableFuture.supplyAsync(task, executor).thenAccept(s -> System.out.println(new Date() + " @ " +
//                    s)).get();
//            CompletableFuture.supplyAsync(task, executor).thenAccept(s -> System.out.println(new Date() + " @ " +
//                    s)).get();
//            CompletableFuture.supplyAsync(task, executor).thenAccept(s -> System.out.println(new Date() + " @ " +
//                    s)).get();
//            // todo warum werden hier einzeln und nicht in 10er-Mengen abgearbeitet?
//        }
//        executor.shutdown();
//    }
}
