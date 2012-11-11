package com.mikecouturier.tews;

public class PathSpecification {
    private String path;

    public PathSpecification(String path) {
        this.path = path;
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, path);
    }

    public void server(int port) throws Exception {
        Servers.start(port, path);
    }
}
