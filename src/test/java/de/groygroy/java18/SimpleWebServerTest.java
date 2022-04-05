package de.groygroy.java18;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleWebServerTest {

    private HttpServer mockServer;
    private int aufrufe;

    @BeforeEach
    void before() throws Exception {

        aufrufe = 0;
        var handler = new DummyHandler();
        var filter = Filter.beforeHandler("Anfragen Zählen und Zeigen",
                r -> {
                    aufrufe++;
                    System.out.println("Anfrage [" + r.getRequestMethod() + "] " + r.getRequestURI());
                });
        mockServer = HttpServer.create(new InetSocketAddress(8080), 10, "/", handler, filter);
        mockServer.start();
    }

    @AfterEach
    void after() throws Exception {
        mockServer.stop(0);
    }

    @Test
    void test() throws Exception {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/" + UUID.randomUUID())).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "status");
        assertEquals(1, aufrufe, "aurufe #");
    }

    private class DummyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] antwort = "Hello World".getBytes();
            exchange.sendResponseHeaders(200, antwort.length);
            OutputStream out = exchange.getResponseBody();
            out.write(antwort);
            out.close();
        }
    }

    ;
}