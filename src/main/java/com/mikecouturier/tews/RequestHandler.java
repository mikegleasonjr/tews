package com.mikecouturier.tews;

import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestHandler extends AbstractHandler {
    private boolean called = false;
    private String lastUrlRequested;

    public boolean isCalled() {
        return called;
    }

    public String getLastUrlRequested() {
        return lastUrlRequested;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
        lastUrlRequested = request.getRequestURI();
        called = true;
    }
}
