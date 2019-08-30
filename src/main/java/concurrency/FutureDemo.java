package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        @SuppressWarnings("Convert2Lambda")
        Future<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(20);
                return "Hello, World! in lambda";
            }
        });

        System.out.println("Processing...");

        getIfNotCancelled(future);

        future = service.submit(() -> {
            // hier komt ie als ie klaar is
            Thread.sleep(10);
            return "Hello, World! 1";
        });

        System.out.println("More processing...");

        while (!future.isDone()) {
            System.out.println("Waiting...");
        }

        getIfNotCancelled(future);

        future = service.submit(() -> {
            Thread.sleep(10);
            return "Hello, World! 2";
        });

        future.cancel(true);

        System.out.println("Even more processing...");

        getIfNotCancelled(future);

        if (!service.isShutdown())
            service.shutdown();
    }

    private static void getIfNotCancelled(Future<String> future) {
        try {
            if (!future.isCancelled()) {
                System.out.println(future.get());
            } else {
                System.out.println("Cancelled");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /*
    Processing...
Hello, World! in lambda
More processing...
Waiting...
Waiting...
    Waiting...
Waiting...
Waiting...
Waiting...
Waiting...
Waiting...
Hello, World! 1
Even more processing...
Cancelled

     */
}
