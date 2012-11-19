package com.mikecouturier.tews;

public class Chain {
    private ChainMemory chainMemory;

    protected Chain(ChainMemory chainMemory) {
        this.chainMemory = chainMemory;
    }

    protected ResponseDefinition getCurrentResponseDefinition() {
        return this.chainMemory.getCurrentLink().getResponseDefinition();
    }

    protected RequestDefinition getCurrentRequestDefinition() {
        return this.chainMemory.getCurrentLink().getRequestDefinition();
    }

    public PathDefinition serve(String path) {
        return chainMemory.advanceChain(path).getPathDefinition();
    }

    public void server() throws Exception {
        server(Tews.DEFAULT_PORT);
    }

    public void server(int port) throws Exception {
        Servers.start(this.chainMemory, port);
    }
}
