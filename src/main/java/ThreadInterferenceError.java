import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadInterferenceError {

    public static void main(String[] args) throws InterruptedException {

        multithreadWithIntStream();

    }

    private static void multithreadWithFor() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Counter counter = new Counter();

        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> counter.increment());
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final count is: " + counter.getCount());
    }

    private static void multithreadWithIntStream() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Counter counter = new Counter();

        IntStream.range(0, 1000)
                .forEach(i -> {
                    executorService.submit(() -> counter.increment());
                });

        System.out.println("Counter: " + counter.getCount());
        executorService.shutdown();
    }
}

class Counter {

    int count = 0;


    // Synchronized Method to prevent multiple threads accessing it concurrently. Mo race conditions occur
    public synchronized void increment() {
        count = count + 1;
    }

    public int getCount() {
        return this.count;
    }
}
