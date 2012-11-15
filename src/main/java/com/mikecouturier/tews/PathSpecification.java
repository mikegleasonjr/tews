package com.mikecouturier.tews;

// @todo, clean code

public class PathSpecification {
    private String path;
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;
    private PathSpecification previousPathSpecification = null;

    protected PathSpecification getPreviousPathSpecification() {
        return previousPathSpecification;
    }

    protected String getPath() {
        return path;
    }

    protected RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    protected ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

    public PathSpecification(String path) {
        this.path = path;
        this.requestSpecification = new RequestSpecification(this);
        this.responseSpecification = new ResponseSpecification(this);
    }

    public PathSpecification(String path, PathSpecification previousPathSpecification) {
        this.path = path;
        this.requestSpecification = new RequestSpecification(this);
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

    public RequestSpecification when() {
        return requestSpecification;
    }

    public ResponseSpecification responding() {
        return responseSpecification;
    }
}
