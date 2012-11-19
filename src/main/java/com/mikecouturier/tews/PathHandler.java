package com.mikecouturier.tews;

// todo, clean code

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

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
        Request request = (Request)servletRequest;
        Response response = (Response)servletResponse;
        System.out.println(String.format("[TEWS] http://localhost:%d%s", request.getServerPort(), request.getRequestURI()));

        for (Map.Entry<String, String> parameter : requestDefinition.getParameters().entrySet()) {
            if (!parameter.getValue().equals(request.getParameter(parameter.getKey()))) {
               request.setHandled(false);
               return;
           }
        }

        for (Map.Entry<String, String> header : requestDefinition.getHeaders().entrySet()) {
            if (!request.getHeader(header.getKey()).equals(header.getValue())) {
                request.setHandled(false);
                return;
            }
        }

        if (requestDefinition.getMethod() != null) {
            if (!requestDefinition.getMethod().equalsIgnoreCase(request.getMethod())) {
                request.setHandled(false);
                return;
            }
        }

        if (pathDefinition.getPath().equals("/") && !request.getRequestURI().equals("/")) {
            request.setHandled(false);
            return;
        }

        if (responseDefinition.getBody() != null) {
            servletResponse.getWriter().write(responseDefinition.getBody());
        }

        if (responseDefinition.getContentType() != null) {
            servletResponse.setContentType(responseDefinition.getContentType());
        }

        for (Map.Entry<String, String> header : responseDefinition.getHeaders().entrySet()) {
            response.addHeader(header.getKey(), header.getValue());
        }

        response.setStatus(responseDefinition.getStatusCode());
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
