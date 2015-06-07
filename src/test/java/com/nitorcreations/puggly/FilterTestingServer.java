package com.nitorcreations.puggly;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

public class FilterTestingServer {
    Server server;

    public void start(Filter filterToTest) throws Exception {
        server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new TestServlet()), "/*");
        context.addFilter(new FilterHolder(filterToTest), "/*", EnumSet.of(DispatcherType.REQUEST));
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}