package com.mikecouturier.tews;

import static com.jayway.restassured.RestAssured.get;
import static com.mikecouturier.tews.Tews.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Test;

public class TewsTest {

    @Test
    public void localPortsUsedInTestsAreAvailable() throws Exception {
        portAvailable(Tews.DEFAULT_PORT);
        portAvailable(CUSTOM_PORT);
    }

    @Test
    public void serverStartsOnDefaultPort() throws Exception {
        server();
        portInUse(Tews.DEFAULT_PORT);
    }

    @Test
    public void serverStops() throws Exception {
        server();
        stop();
        portAvailable(Tews.DEFAULT_PORT);
    }

    @Test
    public void serverStartsOnCustomPort() throws Exception {
        server(CUSTOM_PORT);
        portInUse(CUSTOM_PORT);
    }

    @Test
    public void serverStopsFromCustomPort() throws Exception {
        server(CUSTOM_PORT);
        stop(CUSTOM_PORT);
        portAvailable(CUSTOM_PORT);
    }

    @Test
    public void stoppingAServerDoesNotAffectOtherInstances() throws Exception {
        server();
        server(CUSTOM_PORT);

        stop();

        portInUse(CUSTOM_PORT);
    }

    @Test
    public void allRunningServersCanBeStoppedAtOnce() throws Exception {
        server();
        server(CUSTOM_PORT);

        stopAll();

        portAvailable(Tews.DEFAULT_PORT);
        portAvailable(CUSTOM_PORT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void stoppingAStoppedServerThrows() throws Exception {
        stop();
    }

    @Test(expected = IllegalArgumentException.class)
    public void stoppingAStoppedServerOnCustomPortThrows() throws Exception {
        stop(CUSTOM_PORT);
    }

    @Test(expected = AssertionError.class)
    public void aServerWithNoExpectationsShouldThrowAnAssertionWhenARequestIsMade() throws Exception {
        server();
        get("/");
        stop();
    }

    @After
    public void stopServers() throws Exception {
        stopAll();
    }

    private void portAvailable(int port) {
        assertThat("The local port " + port + " is available", PortFinder.available(port), is(true));
    }

    private void portInUse(int port) {
        assertThat("The local port " + port + " is in use", !PortFinder.available(port), is(true));
    }

    private int CUSTOM_PORT = 8082;

    /* example usage
    expect("/test/1").with().header("cache-control", "no-cache").responding().body("ERROR").header("", "").code(300).
    expect("/test/2").once().with().header("cache-control", "no-cache").responding().body("ERROR").
    expect("/test/3").exactly(3).with().header("cache-control", "no-cache").responding().body("ERROR").
    expect("/test/4").twice().with().header("cache-control", "no-cache").responding().body("<title></title>").
    expect("/test/4").once().whenClient().method(POST).header("accept", "text/html").responding().body("yo")
            .server(8123);
    */
}
