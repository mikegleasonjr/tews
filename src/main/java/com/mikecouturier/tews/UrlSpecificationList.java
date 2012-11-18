package com.mikecouturier.tews;

import java.util.ArrayList;
import java.util.List;

public class UrlSpecificationList {
    private List<UrlSpecification> urlSpecificationList = new ArrayList<UrlSpecification>();

    public List<UrlSpecification> getList() {
        return new ArrayList<UrlSpecification>(urlSpecificationList);
    }

    public UrlSpecification createNextUrlSpecification(String path) {
        UrlSpecification nextUrl = new UrlSpecification(path, this);
        urlSpecificationList.add(nextUrl);
        return nextUrl;
    }
}
