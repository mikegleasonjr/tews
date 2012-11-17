package com.mikecouturier.tews;

// @todo, clean code

import java.util.ArrayList;
import java.util.List;

public class UrlSpecification {
    private String path;
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;
    private List<UrlSpecification> urlSpecificationList = null;

    protected String getPath() {
        return path;
    }

    protected RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    protected ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

    protected List<UrlSpecification> getUrlSpecificationList() {
        return urlSpecificationList;
    }

    public UrlSpecification(String path) {
        this.path = path;
        this.requestSpecification = new RequestSpecification(this);
        this.responseSpecification = new ResponseSpecification(this);
        this.urlSpecificationList = new ArrayList<UrlSpecification>();

        this.urlSpecificationList.add(this);
    }

    public UrlSpecification(String path, List<UrlSpecification> urlSpecificationList) {
        this.path = path;
        this.requestSpecification = new RequestSpecification(this);
        this.responseSpecification = new ResponseSpecification(this);
        this.urlSpecificationList = urlSpecificationList;

        this.urlSpecificationList.add(this);
    }

    public void server() throws Exception {
        server(Tews.DEFAULT_PORT);
    }

    public void server(int port) throws Exception {
        Servers.start(this.urlSpecificationList, port);
    }

    public UrlSpecification serve(String path) {
        return new UrlSpecification(path, this.urlSpecificationList);
    }

    public RequestSpecification when() {
        return requestSpecification;
    }

    public ResponseSpecification responding() {
        return responseSpecification;
    }
}
