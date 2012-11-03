package com.mikecouturier.tews;

import com.jayway.restassured.RestAssured;
import com.mikecouturier.tews.builders.WebServerBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.After;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.mikecouturier.tews.builders.WebServerBuilder.aWebServer;
import static org.hamcrest.CoreMatchers.equalTo;

public class WebServerTest {
    @Test
    public void theWebServerStartsByDefaultOnPort8080() throws Exception {
        use(aWebServer());
        get("/");
    }

    @Test(expected = HttpHostConnectException.class)
    public void theWebServerCanBeStopped() throws Exception {
        use(aWebServer()).stop();
        get("/");
    }

    @Test
    public void theWebServerCanBeStartedOnASpecificPort() throws Exception {
        use(aWebServer().onPort(8181));
        given().port(8181).get("/");
    }

    @Test
    public void theWebServerReturnsNothingByDefault() throws Exception {
        use(aWebServer());
        expect().statusCode(404).when().get("/");
    }

    @After
    public void tearDown() throws Exception {
        if (webServer != null) {
            webServer.stop();
        }
        webServer = null;
    }

    private WebServer use(WebServerBuilder webServerBuilder) throws Exception {
        webServer = webServerBuilder.build();
        webServer.start();
        return webServer;
    }

    private WebServer webServer = null;
}
