package com.mikecouturier.tews;

import org.mortbay.jetty.Server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tews {
    public static int DEFAULT_PORT = 8080;
    private static Map<Integer, Server> runningServers = new HashMap<Integer, Server>();

    public static void server() throws Exception {
        server(DEFAULT_PORT);
    }

    public static void server(int port) throws Exception {
        Server s = new Server(port);

        s.setHandler(new RequestHandler());
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

        if (((RequestHandler)s.getHandler()).isCalled()) {
            throw new AssertionError("Unexpected URL requested");
        }

        s.stop();
    }

    public static void stopAll() throws Exception {
        Iterator<Map.Entry<Integer, Server>> it = runningServers.entrySet().iterator();

        while (it.hasNext()) {
            it.next().getValue().stop();
            it.remove();
        }
    }
}
