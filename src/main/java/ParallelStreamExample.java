import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class ParallelStreamExample {


    public static void main(String[] args) {

        testWithoutStream();
        testWithStream();
        testWithStreamAndParallel();
    }


    // Apply without stream
    private static void testWithoutStream () {

        int total = 0;

        long start = System.currentTimeMillis();

        for( int i = 0; i < 10; i++) {
            total = total + multiplyBy2(i);
        }

        long end = System.currentTimeMillis();

        System.out.println("-- testWithoutStream -- Took " + (end - start) + ", to get the sum: " + total);
    }

    // Apply the operation with Stream
    private static void testWithStream () {

        long start = System.currentTimeMillis();

        IntStream intStream = IntStream.range(1, 10);
        int total = intStream.map(ParallelStreamExample::multiplyBy2).sum();

        long end = System.currentTimeMillis();

        System.out.println("-- testWithStream -- Took " + (end - start) + ", to get the sum: " + total);

    }

    // Apply the operation with Stream and Parallel
    private static void testWithStreamAndParallel () {

        long start = System.currentTimeMillis();

        IntStream intStream = IntStream.range(1, 10);
        int total = intStream.parallel().map(ParallelStreamExample::multiplyBy2).sum();

        long end = System.currentTimeMillis();

        System.out.println("-- testWithStreamAndParallel -- Took " + (end - start) + ", to get the sum: " + total);

    }

    public static int multiplyBy2(int num) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return num * 2;
    }
}
