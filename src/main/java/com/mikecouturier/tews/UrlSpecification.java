package com.mikecouturier.tews;

// @todo, clean code

public class UrlSpecification {
    private String path;
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;
    private UrlSpecification previousUrlSpecification = null;

    protected UrlSpecification getPreviousUrlSpecification() {
        return previousUrlSpecification;
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

    protected void setPreviousUrlSpecification(UrlSpecification url) {
        this.previousUrlSpecification = url;
    }

    public UrlSpecification(String path) {
        this.path = path;
        this.requestSpecification = new RequestSpecification(this);
        this.responseSpecification = new ResponseSpecification(this);
    }

    public UrlSpecification(String path, UrlSpecification previousUrlSpecification) {
        this.path = path;
        this.requestSpecification = new RequestSpecification(this);
        this.responseSpecification = new ResponseSpecification(this);
        this.previousUrlSpecification = previousUrlSpecification;
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, this);
    }

    public void server(int port) throws Exception {
        Servers.start(port, this);
    }

    public UrlSpecification serve(String path) {
        return new UrlSpecification(path, this);
    }

    public RequestSpecification when() {
        return requestSpecification;
    }

    public ResponseSpecification responding() {
        return responseSpecification;
    }
}
