package com.mikecouturier.tews;

import java.util.HashMap;
import java.util.Map;

public class ResponseSpecification {
    private PathSpecification path;
    private String body;
    private String contentType = "text/plain";
    private int statusCode = 200;
    private Map<String, String> headers = new HashMap<String, String>();

    protected String getBody() {
        return body;
    }

    protected String getContentType() {
        return contentType;
    }

    protected int getStatusCode() {
        return statusCode;
    }

    protected Map<String, String> getHeaders() {
        return headers;
    }

    public ResponseSpecification(PathSpecification path) {
        this.path = path;
    }

    public ResponseSpecification body(String body) {
        this.body = body;
        return this;
    }

    public ResponseSpecification contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public ResponseSpecification statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseSpecification header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, path);
    }

    public void server(int port) throws Exception {
        Servers.start(port, path);
    }
}
