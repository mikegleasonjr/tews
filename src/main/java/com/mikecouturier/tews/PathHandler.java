package com.mikecouturier.tews;

// @todo, clean code

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

public class PathHandler implements Servlet {
    private PathSpecification pathSpecification;

    public PathHandler(PathSpecification pathSpecification) {
        this.pathSpecification = pathSpecification;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Request request = (Request)servletRequest;
        Response response = (Response)servletResponse;
        System.out.println(String.format("[TEWS] http://localhost:%d%s", request.getServerPort(), request.getRequestURI()));


        for (Map.Entry<String, String> parameter : pathSpecification.getRequestSpecification().getParameters().entrySet()) {
            if (!parameter.getValue().equals(request.getParameter(parameter.getKey()))) {
               request.setHandled(false);
               return;
           }
        }

        for (Map.Entry<String, String> header : pathSpecification.getRequestSpecification().getHeaders().entrySet()) {
            if (!request.getHeader(header.getKey()).equals(header.getValue())) {
                request.setHandled(false);
                return;
            }
        }

        if (pathSpecification.getRequestSpecification().getMethod() != null) {
            if (!pathSpecification.getRequestSpecification().getMethod().equalsIgnoreCase(request.getMethod())) {
                request.setHandled(false);
                return;
            }
        }

        if (pathSpecification.getPath().equals("/") && !request.getRequestURI().equals("/")) {
            request.setHandled(false);
            return;
        }

        if (pathSpecification.getResponseSpecification().getBody() != null) {
            servletResponse.getWriter().write(pathSpecification.getResponseSpecification().getBody());
        }

        if (pathSpecification.getResponseSpecification().getContentType() != null) {
            servletResponse.setContentType(pathSpecification.getResponseSpecification().getContentType());
        }

        for (Map.Entry<String, String> header : pathSpecification.getResponseSpecification().getHeaders().entrySet()) {
            response.addHeader(header.getKey(), header.getValue());
        }

        response.setStatus(pathSpecification.getResponseSpecification().getStatusCode());
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
