package com.mikecouturier.tews;

import org.mortbay.jetty.Request;

public class PathDefinition extends Chain {
    private String path;

    protected String getPath() {
        return path;
    }

    public PathDefinition(ChainMemory chainMemory, String path) {
        super(chainMemory);

        this.path = path;
    }

    public RequestDefinition when() {
        return getCurrentRequestDefinition();
    }

    public ResponseDefinition responding() {
        return getCurrentResponseDefinition();
    }

    protected boolean match(Request request) {
        return !(pathIsRoot() && requestNotRoot(request));
    }

    private boolean requestNotRoot(Request request) {
        return !request.getRequestURI().equals("/");
    }

    private boolean pathIsRoot() {
        return path.equals("/");
    }
}
