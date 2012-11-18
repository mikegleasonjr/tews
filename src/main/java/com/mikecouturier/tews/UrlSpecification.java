package com.mikecouturier.tews;

public class UrlSpecification extends UrlChain {
    private String path;
    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    protected String getPath() {
        return path;
    }

    protected RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    protected ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

    public UrlSpecification(String path, UrlSpecificationList urlSpecificationList) {
        super(urlSpecificationList);

        this.path = path;
        this.requestSpecification = new RequestSpecification(urlSpecificationList);
        this.responseSpecification = new ResponseSpecification(urlSpecificationList);
    }

    public RequestSpecification when() {
        return requestSpecification;
    }

    public ResponseSpecification responding() {
        return responseSpecification;
    }
}
