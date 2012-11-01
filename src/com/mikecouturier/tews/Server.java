package com.mikecouturier.tews;

import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

import javax.servlet.*;
import java.io.IOException;

public class Server {
    private int port;
    private org.mortbay.jetty.Server server;
    private String output = "tews listening...";

    public Server(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void start() throws Exception {
        server = new org.mortbay.jetty.Server(port);

        Context root = new Context(server, "/", Context.DEFAULT);
        root.addServlet(new ServletHolder(new HelloServlet(output)), "/*");

        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void respondWith(String output) {
        this.output = output;
    }

    private static class HelloServlet implements Servlet {
        private String message;

        public HelloServlet(String message) {
            this.message = message;
        }

        @Override
        public void init(ServletConfig servletConfig) throws ServletException {
        }

        @Override
        public ServletConfig getServletConfig() {
            return null;
        }

        @Override
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            servletResponse.getWriter().write(message);
        }

        @Override
        public String getServletInfo() {
            return null;
        }

        @Override
        public void destroy() {
        }
    }
}
