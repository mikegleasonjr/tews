package com.mikecouturier.tews;

import java.util.List;

public class Tews {
    public static int DEFAULT_PORT = 8080;

    public static void server() throws Exception {
        server(DEFAULT_PORT);
    }

    public static void server(int port) throws Exception {
        Servers.start(port);
    }

    public static void server(UrlSpecification url) throws Exception {
        server(url, DEFAULT_PORT);
    }

    public static void server(UrlSpecification url, int port) throws Exception {
        Servers.start(url, port);
    }

    public static void server(List<UrlSpecification> urls) throws Exception {
        server(urls, DEFAULT_PORT);
    }

    public static void server(List<UrlSpecification> urls, int port) throws Exception {
        Servers.start(urls, port);
    }

    public static void stop() throws Exception {
        stop(DEFAULT_PORT);
    }

    public static void stop(int port) throws Exception {
        Servers.stop(port);
    }

    public static void stopAll() throws Exception {
        Servers.stopAll();
    }

    public static UrlSpecification serve(String path) {
        return new UrlSpecification(path);
    }
}
