package com.mikecouturier.tews;

import org.mortbay.jetty.Server;

public class WebServer {
    private int port = 8080;
    private Server server;

    public WebServer() {
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        server = new Server(port);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
