package com.selfio.selfio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class SelfioApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfioApplication.class, args);
    }
}
