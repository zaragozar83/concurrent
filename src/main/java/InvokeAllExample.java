import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAllExample {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        System.out.println("Start the process...");

        Callable<String> task1 = () -> {

            Thread.sleep(2000);
            return "Result Task 1";
        };

        Callable<String> task2 = () -> {

            Thread.sleep(1000);
            return "Result Task 2";
        };


        Callable<String> task3 = () -> {

            Thread.sleep(5000);
            return "Result Task 3";
        };

        List<Callable<String>> taskList = Arrays.asList(task1, task2, task3);

        List<Future<String>> futures = executorService.invokeAll(taskList);

        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        System.out.println("End of the execution");
        executorService.shutdown();

    }
}
