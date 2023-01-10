package com.anton.fomenko.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import javax.net.ssl.SSLParameters;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHelper {

    private static HttpClient httpClient;

    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            SSLParameters parameters = new SSLParameters();
            parameters.setProtocols(new String[]{"TLSv1.2"});
            httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .sslParameters(parameters)
                    .build();
        }
        return httpClient;
    }

    public static String GET_Request(String url, String... headers) {
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1).timeout(Duration.ofMinutes(1)).GET().uri(new URI(url));
            if (headers != null && headers.length != 0) request.headers(headers);
            HttpResponse<String> send = getHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
            return send.body();
        } catch (URISyntaxException ignored) {
        } catch (InterruptedException | IOException e) {
            log.error("[ERROR SEND GET REQUEST] Error send to {}", url);
        }
        return "";
    }

    public static String POST_Request(String url, String params, String... headers) {
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .timeout(Duration.ofMinutes(3))
                    .POST(HttpRequest.BodyPublishers.ofString(params))
                    .uri(new URI(url));

            if (headers != null && headers.length != 0) request.headers(headers);
            HttpResponse<String> stringHttpResponse = getHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());

            return stringHttpResponse.body();
        } catch (URISyntaxException ignored) {
        } catch (InterruptedException e) {
            log.error("Post request error");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("Error " + url);
            log.error(e.getMessage());
        }
        return "";
    }


    public static String PUT_Request(String url, String params, String... headers) {
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .timeout(Duration.ofMinutes(3))
                    .PUT(HttpRequest.BodyPublishers.ofString(params))
                    .uri(new URI(url));

            if (headers != null && headers.length != 0) request.headers(headers);
            HttpResponse<String> stringHttpResponse = getHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());

            return stringHttpResponse.body();
        } catch (URISyntaxException ignored) {
        } catch (InterruptedException e) {
            log.error("Put request error");
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("Error " + url);
            log.error(e.getMessage());
        }
        return "";
    }

    public static String DELETE_Request(String url, String... headers) {
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1).timeout(Duration.ofMinutes(1)).DELETE().uri(new URI(url));
            if (headers != null && headers.length != 0) request.headers(headers);
            HttpResponse<String> send = getHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
            return send.body();
        } catch (URISyntaxException ignored) {
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] getHeaders() {
        return new String[]{
                "Content-Type", "application/json"
        };
    }
}

