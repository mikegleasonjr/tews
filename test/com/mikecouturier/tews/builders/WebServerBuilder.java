package com.mikecouturier.tews.builders;

import com.mikecouturier.tews.WebServer;

public class WebServerBuilder {
    private int port = 8080;
    private String output = null;

    public  static WebServerBuilder aWebServer() {
        return new WebServerBuilder();
    }

    public WebServerBuilder onPort(int port) {
        this.port = port;
        return this;
    }

    public WebServer build() {
        WebServer s = new WebServer(port);

        s.respondWith(output);

        return s;
    }

    public WebServerBuilder whichResponds(String output) {
        this.output = output;
        return this;
    }
}
