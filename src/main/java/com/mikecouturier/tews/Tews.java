package com.mikecouturier.tews;

import org.mortbay.jetty.Server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tews {
    private static Map<Integer, Server> runningServers = new HashMap<Integer, Server>();
    private static int DEFAULT_PORT = 8080;

    public static void run() throws Exception {
        run(DEFAULT_PORT);
    }

    public static void run(int port) throws Exception {
        Server s = new Server(port);
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

       runningServers.remove(port).stop();
    }

    public static void stopAll() throws Exception {
        Iterator<Map.Entry<Integer, Server>> it = runningServers.entrySet().iterator();

        while (it.hasNext()) {
            it.next().getValue().stop();
            it.remove();
        }
    }
}
