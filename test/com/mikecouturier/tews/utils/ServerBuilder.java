package com.mikecouturier.tews.utils;

import com.mikecouturier.tews.Server;

public class ServerBuilder {
    private int port = 8080;
    private String output = null;

    public  static ServerBuilder aServer() {
        return new ServerBuilder();
    }

    public ServerBuilder onPort(int port) {
        this.port = port;
        return this;
    }

    public Server build() {
        Server s = new Server(port);

        if (output != null) {
            s.respondWith(output);
        }

        return s;
    }

    public ServerBuilder whichResponds(String output) {
        this.output = output;
        return this;
    }
}
