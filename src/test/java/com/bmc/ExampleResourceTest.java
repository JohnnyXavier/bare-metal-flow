package com.bmc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

@QuarkusTest
class ExampleResourceTest {

  @Test
  @Disabled("a placeholder to run stress tests manually")
  void testHelloEndpoint() {

    String path = "localhost:8080/v1/seniority/all?pageIx=0&pageSize=100&sortBy=name&sortDir=desc";
    RestAssured.get(path);

    int warmup = 2000;
    int reqs = 40000;

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