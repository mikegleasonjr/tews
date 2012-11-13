package com.mikecouturier.tews;

public class ResponseSpecification {
    private PathSpecification path;
    private String body;

    public ResponseSpecification(PathSpecification path) {
        this.path = path;
    }

    public ResponseSpecification body(String body) {
        this.body = body;
        return this;
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, path);
    }

    public void server(int port) throws Exception {
        Servers.start(port, path);
    }
}
