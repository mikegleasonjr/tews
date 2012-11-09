package com.mikecouturier.tews;

public class LoggedRequest {
    private final String uri;

    public LoggedRequest(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return String.format("%s", uri);
    }
}
