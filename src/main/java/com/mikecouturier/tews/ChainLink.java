package com.mikecouturier.tews;

public class ChainLink {
    private final PathDefinition pathDefinition;
    private final RequestDefinition requestDefinition;
    private final ResponseDefinition responseDefinition;

    public PathDefinition getPathDefinition() {
        return pathDefinition;
    }

    public RequestDefinition getRequestDefinition() {
        return requestDefinition;
    }

    public ResponseDefinition getResponseDefinition() {
        return responseDefinition;
    }

    public ChainLink(ChainMemory chainMemory, String path) {
        this.pathDefinition = new PathDefinition(chainMemory, path);
        this.requestDefinition = new RequestDefinition(chainMemory);
        this.responseDefinition = new ResponseDefinition(chainMemory);
    }
}
