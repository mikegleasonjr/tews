package com.mikecouturier.tews;

import org.junit.After;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static com.mikecouturier.tews.Tews.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
    public void stoppingANonRunningServerThrows() throws Exception {
        stop();
    }

    @Test(expected = IllegalArgumentException.class)
    public void stoppingANonRunningServerOnCustomPortThrows() throws Exception {
        stop(CUSTOM_PORT);
    }

    @Test
    public void aServerStatusCodeIs404ByDefault() throws Exception {
        server();
        expect().statusCode(404).when().get("/");
    }

    @Test
    public void definingAUrlReturnsCode200ByDefault() throws Exception {
        serve("/").server();
        expect().statusCode(200).when().get("/");
        expect().statusCode(404).when().get("/anything-else");
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
    serve("/test/1").when().header("", "").responding().code(500).
    serve("/test/2").once().when().header("", "").responding().body("ERROR").
    serve("/test/3").exactly(3).when().header("", "").responding().body("ERROR").
    serve("/test/4").twice().when().header("", "no-cache").responding().contentType("application/json").body("{ "test": 3 }").
    serve("/test/4").once().when().method(POST).header("accept", "text/html").responding().body("yo")
            .server(8123);
    */
}
