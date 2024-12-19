package org.client;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final String WORKER_ADDRESS = "http://localhost:8080/task";

    public static void main( String[] args )
    {
        Aggregator aggregator = new Aggregator();
        String task = "10,200";
        List<String> results = aggregator.sendTasksToWorkers(List.of(WORKER_ADDRESS), List.of(task));
        for(String result: results) {
            System.out.println(result);
        }
    }
}
