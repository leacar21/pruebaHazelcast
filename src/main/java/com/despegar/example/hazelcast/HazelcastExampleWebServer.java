package com.despegar.example.hazelcast;

import java.util.EnumSet;
import java.util.TimeZone;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.joda.time.DateTimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.despegar.example.hazelcast.context.scan.ServiceContext;
import com.despegar.example.hazelcast.filter.CustomHibernateSessionFilter;



public class HazelcastExampleWebServer {

	private static final String GMT = "GMT";
    private static final int DEFAULT_PORT = 9075;

    public static void main(String[] args) {
        run(DEFAULT_PORT, ServiceContext.class, args);
    }
    
    private static void run(int port, Class<?> webContext, String[] args) {
        try {
            
            TimeZone.setDefault(TimeZone.getTimeZone(GMT));
            DateTimeZone.setDefault(DateTimeZone.UTC);

            GzipHandler handler = buildWebAppContext(webContext);

            HandlerList handlers = new HandlerList();
            handlers.addHandler(handler);

            Server server = buildServer(handler, port);
            server.setHandler(handlers);
            server.setStopAtShutdown(true);
            server.start();
            server.join();

        } catch (RuntimeException e) {
            System.out.println("Fatal RuntimeException: " + e.getMessage());
            System.exit(1); // Default exit code, 0, indicates success. Non-zero value means failure.
        } catch (VirtualMachineError e) {
        		System.out.println("Fatal Virtual Machine Error: " + e.getMessage());
            System.exit(1); // Default exit code, 0, indicates success. Non-zero value means failure.
        } catch (Exception e) {
        		System.out.println("Fatal Exception: " + e.getMessage());
            System.exit(1); // Default exit code, 0, indicates success. Non-zero value means failure.
        }
    }
    
    //----
    
    private static final QueuedThreadPool JETTY_THREAD_POOL = new QueuedThreadPool();

    private static Server buildServer(GzipHandler handler, int port) {
        return buildServer(JETTY_THREAD_POOL, handler, port);
    }

    private static Server buildServer(ThreadPool pool, Handler handler, int port) {
        Server server = new Server(pool);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[] {connector});

        server.setHandler(handler);
        server.setStopAtShutdown(true);
        return server;
    }

    @Bean(name = "jettyThreadPool")
    private QueuedThreadPool getJettyThreadPool() {
        return JETTY_THREAD_POOL;
    }
    
    //----
    

    private static GzipHandler buildWebAppContext(Class<?> webContext) {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(webContext);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        appendSpringDispatcherServlet(applicationContext, handler);
        appendListeners(applicationContext, handler);
        appendFilters(handler);

        applicationContext.close();

        GzipHandler gziphandler = new GzipHandler();
        gziphandler.setHandler(handler);

        return gziphandler;
    }

    private static void appendListeners(AnnotationConfigWebApplicationContext applicationContext, ServletContextHandler handler) {
        // Para que funcione Spring con su contexto
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(applicationContext) {
            @Override
            public void contextInitialized(javax.servlet.ServletContextEvent event) {
                super.contextInitialized(event);
            }
        };

        handler.addEventListener(contextLoaderListener);
    }

    private static void appendSpringDispatcherServlet(AnnotationConfigWebApplicationContext applicationContext, ServletContextHandler handler) {

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext) {
            private static final long serialVersionUID = 1L;
        };

        dispatcherServlet.setDispatchOptionsRequest(true);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        servletHolder.setName("spring");
        servletHolder.setInitOrder(1);
        handler.addServlet(servletHolder, "/*");
    }

    private static void appendFilters(ServletContextHandler handler) {
//		CustomHibernateSessionFilter customHibernateSessionFilter = new CustomHibernateSessionFilter();
//		FilterHolder customHibernateSessionFilterHolder = new FilterHolder(customHibernateSessionFilter);
//		handler.addFilter(customHibernateSessionFilterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
    }

}
