import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Java 8 brings a lot of support for concurrent programming. These are not main topic of this workshop and are just
 * mentioned here for further reading. This class will show you the evolution of concurrency classes in the JDKs on a
 * 10.000 ft level.
 */
public class C_07_Concurrency {

    /**
     * Since JDK 1.0: Runnable.
     * <p>
     * Simple Runnable that cannot be parametrized, cannot throw a checked exception and returns nothing. It just runs.
     */
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());

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
     * Since JDK 1.5: Callable.
     * <p>
     * Here is a simple Callable that is parametrized with a Boolean.
     */
    static class MyCallable implements Callable<Boolean> {

        // Notice: Can throw an exception!
        @Override
        public Boolean call() throws Exception {
            Thread.sleep(1000); // simulate some heavy work here
            System.out.println(Thread.currentThread().getName());

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

        // As you can see, the Callables run on the same thread. To have more control and a comfortable API, there
        // are some useful classes.
    }

    /**
     * Callable is intended to be used in services like the Executor class. Let's have a look at this Callable:
     */
    static class MyCallable2 implements Callable<String> {

        private int id;

        public MyCallable2(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(1000); // simulate some heavy work here
            return Thread.currentThread().getName() + ": " + id;
        }
    }

    /**
     * ... In this test, it's wrapped in a Future and used with an Executor class. This makes it easy to work with
     * tasks.
     */
    @Test
    public void callableWrappedInFuture() throws Exception {

        // The executor sets the environment for the Callable to run in, for example amount of threads.
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Future<String>> list = new ArrayList<>();

        // work with 100 instances of the callable
        for (int i = 0; i < 100; i++) {

            // The executor returns a Future. From JavaDoc: "The Future's get method will return the task's result
            // upon successful completion."
            Future<String> future = executor.submit(new MyCallable2(i));
            list.add(future);
        }

        // Future-objects represent the result of the callable.
        for (Future<String> future : list) {
            // This line gets executed when the Future is ready. That causes the output delay in console.
            System.out.println(new Date() + " @ " + future.get());
        }
        executor.shutdown();
    }


    /**
     * This test shows another feature of Future: it can request the status of the task and thereby create nice
     * feedback for the user.
     */
    @Test
    public void callableWrappedInFuture2() throws Exception {
        Callable<String> quickCallable = () -> {
            Thread.sleep(1000);
            return Thread.currentThread().getName();
        };

        Callable<String> slowCallable = () -> {
            Thread.sleep(2000);
            return Thread.currentThread().getName();
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> quickTask = executor.submit(quickCallable);
        Future<String> slowTask = executor.submit(slowCallable);

        while (true) {
            try {
                if (quickTask.isDone() && slowTask.isDone()) {
                    System.out.println("Both tasks done!");
                    executor.shutdown();
                    return;
                }

                if (!quickTask.isDone()) {
                    System.out.println("quickTask output: " + quickTask.get());
                }

                System.out.println("Waiting for slowTask to complete");
                String s = slowTask.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("slowTask output: " + s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }
    }


    /**
     * Since JDK 1.8: CompletableFuture
     * <p>
     * Since recently, we can write fluent API, for example in JavaFX and in streams. CompletableFuture lets us also
     * write fluent API in concurrent code.
     */
    @Test
    public void CompletableFuture() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Here, a task is defined. Notice that it's defined as a Supplier, not a Callable. That's necessary for the
        // fluent API because  Supplier doesn't throw exceptions.
        Supplier<String> task = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Thread.currentThread().getName();
        };

        List<CompletableFuture<String>> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(task, executor);
            list.add(completableFuture);
        }

        for (CompletableFuture<String> future : list) {
            // Here it is, our fluent API. After the CompletableFuture finished, we can just add another task to work
            // on. Here, it's just a println:
            future.thenAccept(s -> System.out.println(new Date() + " @ " + s)).get();
        }

        executor.shutdown();
    }

    /*
    Other changes in JDK 1.8:
    - redesign class ForkJoinPool: had just one submit queue for external tasks, now has several. Much more
      performance for applications with a lot of users that submit tasks simultaneously.
    - common pool: ForkJoinPool has new method commonPool() that returns a singleton instance of the ForkJoinPool.
      Used for parallel streams.
    - new ForkJoinTask besides existing RecursiveTask and RecursiveAction: CountedCompleter. All three classes
      are used for recursive programming. CountedCompleter builds a tree structure of java objects while traversing
      the recursion
    - better accumulators
    - new lock: StampedLock
    */

    /*
        Concurrency should really be explained more in a separate tutorial and is scratched only slightly here. Hence,
        no exercises.
     */

    // Repetition is a source of learning:
    // Why are Date and Time objects mutable?
    // -> Only just one question here because code samples for specific problems can be searched when used.
}
