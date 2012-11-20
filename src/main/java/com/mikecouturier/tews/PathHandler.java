package com.mikecouturier.tews;

// todo, clean code

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;

import javax.servlet.*;
import java.io.IOException;

public class PathHandler implements Servlet {
    private PathDefinition pathDefinition;
    private RequestDefinition requestDefinition;
    private ResponseDefinition responseDefinition;

    public PathHandler(PathDefinition pathDefinition, RequestDefinition requestDefinition, ResponseDefinition responseDefinition) {
        this.pathDefinition = pathDefinition;
        this.requestDefinition = requestDefinition;
        this.responseDefinition = responseDefinition;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Request request = (Request) servletRequest;
        Response response = (Response) servletResponse;

        log(request);

        if (!pathDefinition.match(request) || !requestDefinition.match(request)) {
            request.setHandled(false);
        } else {
            responseDefinition.fill(response);
        }
    }

    private void log(Request request) {
        System.out.println(String.format("[TEWS] http://localhost:%d%s", request.getServerPort(), request.getRequestURI()));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
    }
}
