package com.mikecouturier.tews;

public class Tews {
    public static int DEFAULT_PORT = 8080;

    public static void server() throws Exception {
        server(DEFAULT_PORT);
    }

    public static void server(int port) throws Exception {
        Servers.start(port);
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
        return new UrlSpecificationList().createNextUrlSpecification(path);
    }
}
