package com.mikecouturier.tews;

import com.mikecouturier.tews.servlets.RawOutputServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import static com.mikecouturier.tews.utils.Coalesce.coalesce;

public class WebServer {
    private int port;
    private Server server;
    private String output = "tews listening...";

    public WebServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        createServer();
        bindServletToServer();
        serveRequests();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void respondWith(String output) {
        this.output = coalesce(output, "");
    }

    private void createServer() {
        server = new Server(port);
    }

    private void bindServletToServer() {
        Context root = new Context(server, "/", Context.DEFAULT);
        root.addServlet(new ServletHolder(new RawOutputServlet(output)), "/*");
    }

    private void serveRequests() throws Exception {
        server.start();
    }
}
