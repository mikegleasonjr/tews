package com.mikecouturier.tews;

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
}
