import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class WorkingWithCallables {

    public static void main(String[] args) throws ExecutionException, InterruptedException{

//        testSimpleCallable();
//        invokeAllTest();
        invokeAnyTest();
    }

    private static void testSimpleCallable () throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Integer> task = () -> {
            try{

                TimeUnit.SECONDS.sleep(1);
                return 7;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        Future<Integer> future = executorService.submit(task);

        System.out.println("Is the Future done? " + future.isDone());


        Integer result = 0;
        try {
            result = future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new IllegalStateException("Waiting long time without result");
        }

        System.out.println("Is the Future done? " + future.isDone());
        System.out.println("Result is: " + result);

        executorService.shutdown();

    }

    private static void invokeAllTest() {

        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "Task 1",
                () -> "Task 2",
                () -> "Task 3",
                () -> "Task 4",
                () -> "Task 5",
                () -> "Task 6",
                () -> "Task 7"
        );

        try {
            executorService.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try{
                            return future.get();
                        } catch (ExecutionException e) {
                            throw new IllegalStateException("Could not get the value from the Future");
                        }catch (InterruptedException e) {
                            throw new IllegalStateException("Could not get the value from the Future");
                        }
                    }).forEach(System.out::println);

        } catch (InterruptedException e) {
            throw new IllegalStateException("Error processing the tasks");
        }

        executorService.shutdown();
    }

    private static void invokeAnyTest() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<String>> callables = Arrays.asList(
               callable("Task 1", 5),
                callable("Task 2", 3),
                callable("Task 3", 7),
                callable("Task 4", 8),
                callable("Task 5", 1),
                callable("Task 6", 9),
                callable("Task 7", 2)
        );

        String result = executorService.invokeAny(callables);
        System.out.println("Result: " + result);

        executorService.shutdown();
    }

    private static Callable<String> callable(String result, long sleepSeconds) {

        return () -> {
          TimeUnit.SECONDS.sleep(sleepSeconds);
          return result;
        };
    }
}
