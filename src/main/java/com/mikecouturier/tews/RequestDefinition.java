package com.mikecouturier.tews;

import java.util.HashMap;
import java.util.Map;

public class RequestDefinition extends Chain {
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

    public RequestDefinition(ChainMemory chainMemory) {
        super(chainMemory);
    }

    public RequestDefinition method(String method) {
        this.method = method;
        return this;
    }

    public RequestDefinition header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public RequestDefinition headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public RequestDefinition param(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public RequestDefinition params(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
        return this;
    }

    public ResponseDefinition responding() {
        return getCurrentResponseDefinition();
    }
}
