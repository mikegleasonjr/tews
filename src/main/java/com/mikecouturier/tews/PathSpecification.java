package com.mikecouturier.tews;

import java.util.ArrayList;
import java.util.List;

public class PathSpecification {
    private List<String> paths = new ArrayList<String>();

    public PathSpecification(String path) {
        serve(path);
    }

    public void server() throws Exception {
        Servers.start(Tews.DEFAULT_PORT, paths);
    }

    public void server(int port) throws Exception {
        Servers.start(port, paths);
    }

    public PathSpecification serve(String path) {
        paths.add(path);
        return this;
    }
}
