package com.mikecouturier.tews;


import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import java.util.*;

public class Servers {
    private static Map<Integer, org.mortbay.jetty.Server> runningServers = new HashMap<Integer, org.mortbay.jetty.Server>();

    public static void start(int port) throws Exception {
        start(port, Collections.<String>emptyList());
    }

    public static void start(int port, List<String> paths) throws Exception {
        org.mortbay.jetty.Server server = new org.mortbay.jetty.Server(port);

        Context defaultContext = new Context(server, "/");
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            defaultContext.addServlet(new ServletHolder(new PathHandler(path)), path);
        }

        server.start();
        runningServers.put(port, server);
    }

    public static void stop(int port) throws Exception {
        if (!runningServers.containsKey(port)) {
            throw new IllegalArgumentException("No server running at port: " + port);
        }

        org.mortbay.jetty.Server s = runningServers.remove(port);
        s.stop();
    }

    public static void stopAll() throws Exception {
        for (Integer port : new HashSet<Integer>(runningServers.keySet())) {
            stop(port);
        }
    }
}