package org.client.networking;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * WebClient is a utility class for sending asynchronous HTTP requests using Java's HttpClient.
 * It is designed to handle HTTP POST requests with a given payload and return the server's response as a String.
 */
public class WebClient {
    private HttpClient client;

    public WebClient() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    /**
     * Sends an asynchronous HTTP POST request to the specified URL with the provided payload.
     *
     * @param url The target URL to which the HTTP request will be sent.
     * @param requestPayload The byte array representing the payload to be included in the HTTP POST request body.
     * @return A CompletableFuture containing the response body as a String, provided by the server upon successful reception of the HTTP POST request.
     */
    public CompletableFuture<String> sendAsync(String url, byte[] requestPayload) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
