package com.mikecouturier.tews;

import org.mortbay.jetty.Request;

import java.util.HashMap;
import java.util.Map;

public class RequestDefinition extends Chain {
    private String method;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> parameters = new HashMap<String, String>();

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

    protected boolean match(Request request) {
        for (Map.Entry<String, String> parameter : this.parameters.entrySet()) {
            if (!parameter.getValue().equals(request.getParameter(parameter.getKey()))) {
                return false;
            }
        }

        for (Map.Entry<String, String> header : this.headers.entrySet()) {
            if (!request.getHeader(header.getKey()).equals(header.getValue())) {
                return false;
            }
        }

        if (method != null) {
            if (!method.equalsIgnoreCase(request.getMethod())) {
                return false;
            }
        }

        return true;
    }
}
