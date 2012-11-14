package com.mikecouturier.tews;

import org.junit.After;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static com.mikecouturier.tews.Tews.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
    public void servingAUrlReturnsCode200() throws Exception {
        serve("/").server();

        expect().statusCode(200).when().get("/");
    }

    @Test
    public void servingTheRootDoesNotServeAnythingElse() throws Exception {
        serve("/").server();

        expect().statusCode(404).when().get("/anything-else");
    }

    @Test
    public void aUrlDefaultContentTypeIsTextPlain() throws Exception {
        serve("/").server();

        expect().contentType("text/plain").when().get("/");
    }

    @Test
    public void aServerCanServeMultipleUrls() throws Exception {
        serve("/url/1").serve("/index.html").server();

        expect().statusCode(200).when().get("/url/1");
        expect().statusCode(200).when().get("/index.html");
    }

    @Test
    public void aUrlCanServeData() throws Exception {
        String data = "data";

        serve("/url").responding().body(data).server();

        expect().body(equalTo(data)).when().get("/url");
    }

    @Test
    public void aUrlCanServeACustomContentType() throws Exception {
        String contentType = "application/json";

        serve("/json").responding().contentType(contentType).server();

        expect().contentType(contentType).when().get("/json");
    }

    @Test
    public void aUrlCanServeACustomStatusCode() throws Exception {
        int statusCode = 401;

        serve("/unauthorized").responding().statusCode(statusCode).server();

        expect().statusCode(statusCode).when().get("/unauthorized");
    }

    @Test
    public void aUrlCanServeACustomHeader() throws Exception {
        String headerName = "Cache-Control";
        String headerValue = "no-cache";

        serve("/uncached/page").responding().header(headerName, headerValue).server();

        expect().header(headerName, headerValue).when().get("/uncached/page");
    }

    @Test
    public void aUrlCanServeMultipleCustomHeaders() throws Exception {
        String headerName1 = "Cache-Control";
        String headerValue1 = "no-cache";
        String headerName2 = "Authorization";
        String headerValue2 = "Basic";

        serve("/multiple-headers").responding()
                .header(headerName1, headerValue1)
                .header(headerName2, headerValue2)
                .server();

        expect().header(headerName1, headerValue1)
                .header(headerName2, headerValue2)
                .when().get("/multiple-headers");
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
