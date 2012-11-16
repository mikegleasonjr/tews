package com.mikecouturier.tews;

// @todo, clean code

import java.util.HashMap;
import java.util.Map;

public class RequestSpecification {
    private PathSpecification path;
    private String method;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> parameters = new HashMap<String, String>();

    protected String getMethod() {
        return method;
    }

    protected Map<String, String> getHeaders() {
        return headers;
    }

    protected Map<String, String> getParameters() {
        return parameters;
    }

    public RequestSpecification(PathSpecification path) {
        this.path = path;
    }

    public RequestSpecification method(String method) {
        this.method = method;
        return this;
    }

    public RequestSpecification header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public RequestSpecification headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public RequestSpecification param(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public RequestSpecification params(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
        return this;
    }

    public ResponseSpecification responding() {
        return path.getResponseSpecification();
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, path);
    }

    public void server(int port) throws Exception {
        Servers.start(port, path);
    }
}
