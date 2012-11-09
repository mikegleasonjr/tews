package com.mikecouturier.tews;

import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestInterceptor extends AbstractHandler {
    private List<LoggedRequest> logs = new ArrayList<LoggedRequest>();

    public List<LoggedRequest> getLogs() {
        return logs;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
        logs.add(new LoggedRequest(target));
    }
}
