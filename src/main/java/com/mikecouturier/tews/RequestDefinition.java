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
        return matchParameters(request) && matchHeaders(request) && matchMethod(request);
    }

    private boolean matchParameters(Request request) {
        for (Map.Entry<String, String> parameter : this.parameters.entrySet()) {
            if (!parameter.getValue().equals(request.getParameter(parameter.getKey()))) {
                return false;
            }
        }
        return true;
    }

    private boolean matchHeaders(Request request) {
        for (Map.Entry<String, String> header : this.headers.entrySet()) {
            if (!request.getHeader(header.getKey()).equals(header.getValue())) {
                return false;
            }
        }
        return true;
    }

    private boolean matchMethod(Request request) {
        return  method == null || method.equalsIgnoreCase(request.getMethod());
    }
}
