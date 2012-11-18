package com.mikecouturier.tews;

import java.util.HashMap;
import java.util.Map;

public class ResponseSpecification extends UrlChain {
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

    public ResponseSpecification(UrlSpecificationList urlSpecificationList) {
        super(urlSpecificationList);
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

    public ResponseSpecification headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }
}
