package com.mikecouturier.tews;

import com.mikecouturier.tews.utils.ServerBuilder;
import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.mikecouturier.tews.utils.ServerBuilder.aServer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ServerTest {
    @Test
    public void aServerRespondsToRequests() throws Exception {
        given(aServer().onPort(8123));

        whenRequestingPath("/");

        thenTheServerResponds();
    }

    @Test
    public void aServerCanBeStopped() throws Exception {
        given(aServer().onPort(8123));

        whenStoppingTheServer();
        whenRequestingPath("/");

        thenTheServerDoesNotRespond();
    }

    @Test
    public void aServerRespondsWithASpecificOutput() throws Exception {
        given(aServer().onPort(8123).whichResponds("Hello World!"));

        whenRequestingPath("/");

        thenTheServerRespondsWith("Hello World!");
    }

    @After
    public void tearDown() throws Exception {
        if (server != null) {
            server.stop();
        }
        server = null;
        responded = false;
        output = null;
    }

    private void given(ServerBuilder serverBuilder) throws Exception {
        server = serverBuilder.build();
        server.start();
    }

    private void whenRequestingPath(String path) throws IOException {
        URL url = new URL(String.format("http://localhost:%d%s", server.getPort(), path));
        URLConnection connection = url.openConnection();
        BufferedReader reader;

        try {
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            output = new StringBuilder();

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                output.append(inputLine);
            }

            reader.close();
            responded = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void whenStoppingTheServer() throws Exception {
        server.stop();
    }

    private void thenTheServerResponds() {
        assertThat(responded, is(true));
    }

    private void thenTheServerDoesNotRespond() {
        assertThat(responded, is(false));
    }

    private void thenTheServerRespondsWith(String expectedOutput) {
        assertThat(output.toString(), equalTo(expectedOutput));
    }

    private Server server = null;
    private boolean responded = false;
    private StringBuilder output = null;
}
