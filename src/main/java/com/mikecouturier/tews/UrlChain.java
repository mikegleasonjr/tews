package com.mikecouturier.tews;

public class UrlChain {
    private UrlSpecificationList urlSpecificationList;

    protected UrlChain(UrlSpecificationList urlSpecificationList) {
        this.urlSpecificationList = urlSpecificationList;
    }

    public UrlSpecification serve(String path) {
        return urlSpecificationList.createNextUrlSpecification(path);
    }

    public void server() throws Exception {
        server(Tews.DEFAULT_PORT);
    }

    public void server(int port) throws Exception {
        Servers.start(this.urlSpecificationList, port);
    }
}
