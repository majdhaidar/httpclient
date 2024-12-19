package org.client;

import org.client.networking.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The Aggregator class is responsible for delegating tasks to multiple workers simultaneously
 * and collecting their responses once the tasks have been completed. It uses asynchronous
 * HTTP communication to achieve its functionality.
 *
 * The Aggregator internally utilizes a WebClient instance to send HTTP POST requests
 * containing task information to the worker addresses, allowing for efficient task distribution.
 */
public class Aggregator {
    private WebClient client;

    public Aggregator() {
        this.client = new WebClient();
    }

    /**
     * Sends a list of tasks to a corresponding list of worker addresses asynchronously
     * and collects the results after the tasks have been completed.
     *
     * @param workersAddresses A list of worker addresses to which tasks will be sent. Each address corresponds to an individual worker.
     * @param tasks A list of tasks to be sent to the workers. Each task corresponds to a worker address.
     * @return A list of results returned by the workers upon task completion, in the same order as the input lists.
     */
    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        CompletableFuture<String>[] futures = new CompletableFuture[workersAddresses.size()];
        for (int i = 0; i < workersAddresses.size(); i++) {
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);

            byte[] requestPayload = task.getBytes();
            futures[i] = client.sendAsync(workerAddress, requestPayload);
        }

        List<String> results = new ArrayList<>();
        for(int i=0; i<futures.length; i++) {
            results.add(futures[i].join());
        }
        return results;
    }
}
