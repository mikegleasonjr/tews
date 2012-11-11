package com.mikecouturier.tews;

import org.mortbay.jetty.Request;

import javax.servlet.*;
import java.io.IOException;

public class PathHandler implements Servlet {
    private String path;

    public PathHandler(String path) {
        this.path = path;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Request request = (Request)servletRequest;

        System.out.println(String.format("[TEWS] http://localhost:%d%s", request.getServerPort(), request.getRequestURI()));

        // Ugly hack, mapping the root catch all URLs on server
        // see "Default Mapping":
        // http://account.pacip.com/jetty/doc/PathMapping.html
        if (path.equals("/") && !request.getRequestURI().equals("/")) {
            request.setHandled(false);
            return;
        }
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
