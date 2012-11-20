package com.mikecouturier.tews;

import org.mortbay.jetty.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseDefinition extends Chain {
    private String body;
    private String contentType = "text/plain";
    private int statusCode = 200;
    private Map<String, String> headers = new HashMap<String, String>();

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

    protected void fill(Response response) throws IOException {
        if (this.body != null) {
            response.getWriter().write(body);
        }

        if (contentType != null) {
            response.setContentType(contentType);
        }

        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.addHeader(header.getKey(), header.getValue());
        }

        response.setStatus(statusCode);
    }
}
