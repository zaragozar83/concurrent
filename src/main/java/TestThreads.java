import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreads {

    public static void main(String[] args) {


//        testRunnableSimple();
//        testRunnableWithSleep();
//        testExecutorsFramework();
        String result = testTryFinally();
        System.out.println("Result: " + result);
    }

    private static void testRunnableSimple() {

        Runnable task = () -> {

            String threadName = Thread.currentThread().getName();
            System.out.println("Welcome to thread: " + threadName);
        };

        task.run();
        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!!!");
    }

    private static void testRunnableWithSleep() {

        Runnable runnable = () -> {

            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Foo: " + threadName);
                TimeUnit.SECONDS.sleep(10);
                System.out.println("Bar: " + threadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Done!!!");
    }

    // Working with Executors framework
    private static void testExecutorsFramework() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Welcome to thread: " + threadName);
            });
        }

        executorService.shutdown();
    }

    private static String testTryFinally() {

        System.out.println("Start process");
        try {
            throw new RuntimeException("Custom Exception");
//            return "Welcome";
        }catch (Exception e) {
            System.out.println("State exception");
            return "Custom Exception -->";

        } finally {
            return "Finally execution.";
        }

    }
}
