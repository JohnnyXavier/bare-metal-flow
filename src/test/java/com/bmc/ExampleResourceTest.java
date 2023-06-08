package com.bmc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@QuarkusTest
class ExampleResourceTest {

    @Test
    @Disabled("a placeholder to run silly stress tests manually from here and sometimes continuously from dev console")
    void testHelloEndpointPost() {

        String path = "/v1/users";

        int reqs = 20000;

        Instant start = Instant.now();

        for (int i = 0; i < reqs; i++) {
            String email = "unique_mail_" + i + "@example.net";
            Map<String, String> bodyMapUser = Map.of("callSign", "callSSSSSign",
                "email", email);

            RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(bodyMapUser)
                .post(path);
        }

        long totalTime = Duration.between(start, Instant.now()).toMillis();

        double reqTime = (double) totalTime / reqs;

        double reqsSec = (double) reqs / totalTime * 1000;

        System.out.println("run 2: processing [" + reqs + "] requests took: " + totalTime + "ms");
        System.out.println("run 2: each request took: " + reqTime + "ms");
        System.out.println("run 2: requests per second: " + reqsSec);

    }


    @Test
    @Disabled("a placeholder to run silly stress tests manually from here and sometimes continuously from dev console")
    void testHelloEndpointGet() {

        String path = "/v1/users/project/2ad49ee4-c629-4bb5-9bd4-a59de16a8c72?pageIx=0&pageSize=5&sortBy=name&sortDir=desc";
        RestAssured.get(path);

        int warmup = 2000;
        int reqs   = 40000;

        Instant start = Instant.now();
        for (int i = 0; i < warmup; i++) {
            RestAssured.get(path);
        }

        long totalTime = Duration.between(start, Instant.now()).toMillis();

        double reqTime = (double) totalTime / warmup;

        double reqsSec = (double) warmup / totalTime * 1000;

        System.out.println("run 1: processing [" + warmup + "] requests took: " + totalTime + "ms");
        System.out.println("run 1: each request took: " + reqTime + "ms");
        System.out.println("run 1: requests per second: " + reqsSec);

        start = Instant.now();
        for (int i = 0; i < reqs; i++) {
            RestAssured.get(path);
        }

        totalTime = Duration.between(start, Instant.now()).toMillis();

        reqTime = (double) totalTime / reqs;

        reqsSec = (double) reqs / totalTime * 1000;

        System.out.println("run 2: processing [" + reqs + "] requests took: " + totalTime + "ms");
        System.out.println("run 2: each request took: " + reqTime + "ms");
        System.out.println("run 2: requests per second: " + reqsSec);
    }

}