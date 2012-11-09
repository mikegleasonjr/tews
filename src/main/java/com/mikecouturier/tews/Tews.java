package com.mikecouturier.tews;

import org.mortbay.jetty.Server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class Tews {
    public static int DEFAULT_PORT = 8080;
    private static Map<Integer, Server> runningServers = new HashMap<Integer, Server>();

    public static void server() throws Exception {
        server(DEFAULT_PORT);
    }

    public static void server(int port) throws Exception {
        Server s = new Server(port);

        s.setHandler(new RequestInterceptor());
        s.start();

        runningServers.put(port, s);
    }

    public static void stop() throws Exception {
        stop(DEFAULT_PORT);
    }

    public static void stop(int port) throws Exception {
        if (!runningServers.containsKey(port)) {
            throw new IllegalArgumentException("No server running at port: " + port);
        }

        Server s = runningServers.remove(port);
        s.stop();

        RequestInterceptor interceptor = (RequestInterceptor)s.getHandler();
        assertThat("unexpected requests made on server", interceptor.getLogs(), empty());
    }

    public static void stopAll() throws Exception {
        for (Integer port : new HashSet<Integer>(runningServers.keySet())) {
            stop(port);
        }
    }
}
