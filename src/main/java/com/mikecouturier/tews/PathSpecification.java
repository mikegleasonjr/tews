package com.mikecouturier.tews;

public class PathSpecification {
    private String path;
    private ResponseSpecification responseSpecification;
    private PathSpecification previousPathSpecification = null;

    protected PathSpecification getPreviousPathSpecification() {
        return previousPathSpecification;
    }

    protected String getPath() {
        return path;
    }

    protected ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

    public PathSpecification(String path) {
        this.path = path;
        this.responseSpecification = new ResponseSpecification(this);
    }

    public PathSpecification(String path, PathSpecification previousPathSpecification) {
        this.path = path;
        this.responseSpecification = new ResponseSpecification(this);
        this.previousPathSpecification = previousPathSpecification;
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
        return responseSpecification;
    }
}
