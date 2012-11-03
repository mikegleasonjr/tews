package com.mikecouturier.tews.builders;

import com.mikecouturier.tews.WebServer;

public class WebServerBuilder {
    private int port = -1;

    public  static WebServerBuilder aWebServer() {
        return new WebServerBuilder();
    }

    public WebServerBuilder onPort(int port) {
        this.port = port;
        return this;
    }

    public WebServer build() {
        WebServer s = new WebServer();

        if (port != -1) {
            s.setPort(port);
        }

        return s;
    }
}
