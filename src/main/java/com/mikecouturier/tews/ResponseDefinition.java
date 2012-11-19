package com.mikecouturier.tews;

import java.util.HashMap;
import java.util.Map;

public class ResponseDefinition extends Chain {
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

    public ResponseDefinition(ChainMemory chainMemory) {
        super(chainMemory);
    }

    public ResponseDefinition body(String body) {
        this.body = body;
        return this;
    }

    public ResponseDefinition contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public ResponseDefinition statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseDefinition header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public ResponseDefinition headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }
}
