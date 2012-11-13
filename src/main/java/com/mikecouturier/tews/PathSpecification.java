package com.mikecouturier.tews;

public class PathSpecification {
    private String path;
    private PathSpecification previous = null;

    public PathSpecification(String path) {
        this.path = path;
    }

    public PathSpecification(String path, PathSpecification previous) {
        this.path = path;
        this.previous = previous;
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, this);
    }

    public void server(int port) throws Exception {
        Servers.start(port, this);
    }

    public PathSpecification serve(String path) {
        return new PathSpecification(path, this);
    }

    public ResponseSpecification responding() {
        return new ResponseSpecification(this);
    }

    public PathSpecification getPrevious() {
        return previous;
    }

    @Override
    public String toString() {
        return path;
    }
}
