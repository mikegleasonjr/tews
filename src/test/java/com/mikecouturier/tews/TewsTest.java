package com.mikecouturier.tews;

import org.apache.http.conn.HttpHostConnectException;
import org.junit.After;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.mikecouturier.tews.Tews.*;

public class TewsTest {

    @Test
    public void serverStartsOnPort8080() throws Exception {
        run();
        get("/");
    }

    @Test(expected = HttpHostConnectException.class)
    public void serverStops() throws Exception {
        run();
        stop();
        get("/");
    }

    @Test
    public void serverStartsOnCustomPort() throws Exception {
        run(8082);
        given().port(8082).get("/");
    }

    @Test(expected = HttpHostConnectException.class)
    public void serverStopsFromCustomPort() throws Exception {
        run(8082);
        stop(8082);
        get("/");
    }

    @Test
    public void stoppingAServerDoesNotAffectOtherInstances() throws Exception {
        run(8081);
        run(8082);

        stop(8081);

        given().port(8082).get("/");
    }

    @Test(expected = HttpHostConnectException.class)
    public void allRunningServersCanBeStoppedAtOnce() throws Exception {
        run();
        run(8081);

        stopAll();

        try {
            get("/");
        } catch(Exception e) {
            given().port(8081).get("/");
        }
    }

    @Test
    public void aNotFoundStatusIsReturnedByDefault() throws Exception {
        run();
        expect().statusCode(404).get("/");
    }

    @After
    public void stopServers() throws Exception {
        stopAll();
    }
}
