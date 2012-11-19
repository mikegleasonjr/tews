package com.mikecouturier.tews;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.mikecouturier.tews.Tews.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TewsTest {

    @Test
    public void localPortsUsedInTestsAreAvailable() throws Exception {
        assertPortAvailable(Tews.DEFAULT_PORT);
        assertPortAvailable(CUSTOM_PORT);
    }

    @Test
    public void serverStartsOnDefaultPort() throws Exception {
        server();
        assertPortInUse(Tews.DEFAULT_PORT);
    }

    @Test
    public void serverStops() throws Exception {
        server();
        stop();
        assertPortAvailable(Tews.DEFAULT_PORT);
    }

    @Test
    public void serverStartsOnCustomPort() throws Exception {
        server(CUSTOM_PORT);
        assertPortInUse(CUSTOM_PORT);
    }

    @Test
    public void serverStopsFromCustomPort() throws Exception {
        server(CUSTOM_PORT);
        stop(CUSTOM_PORT);
        assertPortAvailable(CUSTOM_PORT);
    }

    @Test
    public void stoppingAServerDoesNotAffectOtherInstances() throws Exception {
        server();
        server(CUSTOM_PORT);

        stop();

        assertPortInUse(CUSTOM_PORT);
    }

    @Test
    public void allRunningServersCanBeStoppedAtOnce() throws Exception {
        server();
        server(CUSTOM_PORT);

        stopAll();

        assertPortAvailable(Tews.DEFAULT_PORT);
        assertPortAvailable(CUSTOM_PORT);
    }

    @Test(expected = IllegalStateException.class)
    public void stoppingANonRunningServerThrows() throws Exception {
        stop();
    }

    @Test(expected = IllegalStateException.class)
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
    public void servingAUrlOnACustomPortReturnsCode200() throws Exception {
        serve("/").server(CUSTOM_PORT);

        given().port(CUSTOM_PORT).expect().statusCode(200).when().get("/");
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
    public void canServeAnotherUrlAfterSpecifyingAResponse() throws Exception {
        serve("/url").responding().body("")
                .serve("/another").server();

        expect().statusCode(200).when().get("/url");
        expect().statusCode(200).when().get("/another");
    }

    @Test
    public void aUrlCanServeDataOnACustomPort() throws Exception {
        String data = "data";

        serve("/url").responding().body(data).server(CUSTOM_PORT);

        given().port(CUSTOM_PORT).expect().body(equalTo(data)).when().get("/url");
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

    @Test
    public void headersServedCanBeSpecifiedFromAList() throws Exception {
        Map<String, String> expectedHeaders = new HashMap<String, String>() {{
            put("Date", "Tue, 15 Nov 1994 08:12:31 GMT");
            put("Host", "localhost");
            put("Pragma", "no-cache");
            put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:12.0) Gecko/20100101 Firefox/12.0");
        }};

        serve("/multiple-headers").responding()
                .headers(expectedHeaders)
                .server();

        expect().headers(expectedHeaders)
                .when().get("/multiple-headers");
    }

    @Test
    public void aUrlCanServeOnlyASpecificMethod() throws Exception {
        serve("/method/post").when().method("POST").server();

        expect().statusCode(404).when().get("/method/post");
        expect().statusCode(200).when().post("/method/post");
    }

    @Test
    public void canServeAnotherUrlAfterSpecifyingARequestCondition() throws Exception {
        serve("/url").when().method("GET")
                .serve("/another").server();

        expect().statusCode(200).when().get("/url");
        expect().statusCode(200).when().get("/another");
    }

    @Test
    public void aUrlCanServeOnlyASpecificMethodOnACustomPort() throws Exception {
        serve("/put").when().method("PUT").server(CUSTOM_PORT);

        given().port(CUSTOM_PORT).expect().statusCode(200).when().put("/put");
    }

    @Test
    public void aUrlCanServeOnlyASpecificMethodIrrespectiveOfTheCase() throws Exception {
        serve("/method/post").when().method("pOst").server();

        expect().statusCode(200).when().post("/method/post");
    }

    @Test
    public void aUrlCanOnlyBeServedWhenTheClientSuppliedAParticularHeader() throws Exception {
        String headerName = "Accept";
        String headerValue = "application/json";

        serve("/conditional/header").when().header(headerName, headerValue).server();

        expect().statusCode(404).when().get("/conditional/header");
        given().header(headerName, headerValue).expect().statusCode(200).when().get("/conditional/header");
    }

    @Test
    public void aUrlCanOnlyBeServedWhenTheClientSuppliedMultipleRequestHeaders() throws Exception {
        String headerName1 = "Accept";
        String headerValue1 = "application/json";
        String headerName2 = "Accept-Encoding";
        String headerValue2 = "gzip, deflate";

        serve("/multiple-conditional/headers").when()
                .header(headerName1, headerValue1)
                .header(headerName2, headerValue2)
                .server();

        expect().statusCode(404).when().get("/multiple-conditional/headers");
        given().header(headerName1, headerValue1).header(headerName2, headerValue2).expect().statusCode(200).when().get("/multiple-conditional/headers");
    }

    @Test
    public void aUrlCanOnlyBeServedWhenTheClientSuppliedMultipleRequestHeadersAsAList() throws Exception {
        Map<String, String> suppliedHeaders = new HashMap<String, String>() {{
            put("Accept", "application/json");
            put("Accept-Encoding", "gzip, deflate");
        }};

        serve("/multiple-conditional/headers").when()
                .headers(suppliedHeaders)
                .server();

        expect().statusCode(404).when().get("/multiple-conditional/headers");
        given().headers(suppliedHeaders).expect().statusCode(200).when().get("/multiple-conditional/headers");
    }

    @Test
    public void aRequestHeaderAndAResponseCanBothBeSpecifiedForAUrl() throws Exception {
        serve("/both").when().method("put").responding().body("it works").server();

        expect().statusCode(200).body(equalTo("it works")).when().put("/both");
    }

    @Test
    public void aRequestIsServedWhenASpecificParameterIsExpected() throws Exception {
        String paramName = "param1";
        String paramValue = "value1";

        serve("/query").when().param(paramName, paramValue).server();

        expect().statusCode(404).when().get("/query");
        given().param(paramName, paramValue).expect().statusCode(200).when().get("/query");
    }

    @Test
    public void aRequestIsServedWhenSpecificParametersAreExpected() throws Exception {
        String paramName1 = "param1";
        String paramValue1 = "value1";
        String paramName2 = "param2";
        String paramValue2 = "value2";

        serve("/multiple-query").when()
                .param(paramName1, paramValue1)
                .param(paramName2, paramValue2)
                .server();

        given().param(paramName2, paramValue2).expect().statusCode(404).when().get("/multiple-query");
        given().param(paramName1, paramValue1).param(paramName2, paramValue2).expect().statusCode(200).when().get("/multiple-query");
    }

    @Test
    public void aRequestIsServedWhenSpecificParametersAreExpectedFromList() throws Exception {
        Map<String, String> suppliedParams = new HashMap<String, String>() {{
            put("paramx", "some-value");
            put("paramy", "other-value");
        }};

        serve("/multiple-query/list").when()
                .params(suppliedParams)
                .server();

        expect().statusCode(404).when().get("/multiple-query/list");
        given().params(suppliedParams).expect().statusCode(200).when().get("/multiple-query/list");
    }

    @Test
    public void aPathCanBeSpecifiedSeparatelyFromTheServer() throws Exception {
        Chain chain = serve("/previously-declared1");
        chain.serve("/previously-declared2").responding().body("hello");
        chain.serve("/previously-declared3").when().method("POST");
        chain.server();

        expect().statusCode(200).when().get("/previously-declared1");
        expect().body(equalTo("hello")).statusCode(200).when().get("/previously-declared2");
        expect().statusCode(200).when().post("/previously-declared3");
    }

    @Test
    public void aPathCanBeSpecifiedSeparatelyFromTheServerOnACustomPort() throws Exception {
        Chain chain = serve("/previously-declared1");
        chain.serve("/previously-declared2").responding().body("hello");
        chain.serve("/previously-declared3").when().method("POST");
        chain.server(CUSTOM_PORT);

        given().port(CUSTOM_PORT).expect().statusCode(200).when().get("/previously-declared1");
        given().port(CUSTOM_PORT).expect().body(equalTo("hello")).statusCode(200).when().get("/previously-declared2");
        given().port(CUSTOM_PORT).expect().statusCode(200).when().post("/previously-declared3");
    }

    @After
    public void stopServers() throws Exception {
        stopAll();
    }

    private void assertPortAvailable(int port) {
        assertThat("The local port " + port + " is available", isPortAvailable(port), is(true));
    }

    private void assertPortInUse(int port) {
        assertThat("The local port " + port + " is in use", !isPortAvailable(port), is(true));
    }

    private boolean isPortAvailable(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            serverSocket.close();
            return true;
        } catch (final IOException e) {
            return false;
        }
    }

    private int CUSTOM_PORT = 8082;
}
