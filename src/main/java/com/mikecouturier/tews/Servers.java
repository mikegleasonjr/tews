package com.mikecouturier.tews;

// todo, clean code

import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import java.util.*;

public class Servers {
    private static Map<Integer, org.mortbay.jetty.Server> runningServers = new HashMap<Integer, org.mortbay.jetty.Server>();

    public static void start(int port) throws Exception {
        start(new ChainMemory(), port);
    }

    public static void start(ChainMemory chainMemory, int port) throws Exception {
        org.mortbay.jetty.Server server = new org.mortbay.jetty.Server(port);
        List<ChainLink> chainLinks = chainMemory.getList();

        Context defaultContext = new Context(server, "/");
        for (int i = 0; i < chainLinks.size(); i++) {
            ChainLink chainLink = chainLinks.get(i);
            defaultContext.addServlet(new ServletHolder(new PathHandler(chainLink.getPathDefinition(), chainLink.getRequestDefinition(), chainLink.getResponseDefinition())), chainLink.getPathDefinition().getPath());
        }

        server.start();
        runningServers.put(port, server);
    }

    public static void stop(int port) throws Exception {
        if (!runningServers.containsKey(port)) {
            throw new IllegalStateException("No server running at port: " + port);
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